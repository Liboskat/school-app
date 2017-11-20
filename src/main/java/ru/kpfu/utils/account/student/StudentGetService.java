package ru.kpfu.utils.account.student;

import ru.kpfu.entities.Homework;
import ru.kpfu.entities.Lesson;
import ru.kpfu.entities.Mark;
import ru.kpfu.entities.Subject;
import ru.kpfu.exceptions.authentication.UserSessionNotFoundException;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.utils.time.DateService;
import ru.kpfu.utils.security.UserSessionInteractor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ильшат on 08.11.2017.
 */
public class StudentGetService {
    private static final String TIMETABLE_CATEGORY = "timetable";
    private static final String HOMEWORK_CATEGORY = "homework";
    private static final String MARKS_CATEGORY = "marks";
    private static final String STATISTICS_CATEGORY = "statistics";

    public static void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException, ParseException {
        String login;
        try {
            login = UserSessionInteractor.getUser(request);
        } catch (UserSessionNotFoundException e) {
            response.sendRedirect("/app/login");
            return;
        }
        StudentInformationGetter studentInformationGetter = new StudentInformationGetterImpl(login);

        request.setAttribute("user", studentInformationGetter.getStudent());
        HttpSession session = request.getSession(false);
        String category;
        category = request.getParameter("category");
        if((session != null) && (session.getAttribute("category") != null)) {
            category = (String) session.getAttribute("category");
            session.removeAttribute("category");
        }
        if(category == null) {
            category = TIMETABLE_CATEGORY;
        }

        Integer homeworkWeek;
        Date startOfWeek;
        Date endOfWeek;
        if(request.getParameter("homework_week") == null) {
            Date date = new Date();
            homeworkWeek = DateService.toWeek(date);
            startOfWeek = DateService.getStartOfCurrentWeek();
            endOfWeek = DateService.getEndOfWeek(startOfWeek);
        } else {
            homeworkWeek = Integer.parseInt(request.getParameter("homework_week"));
            startOfWeek = DateService.getStartOfWeek(homeworkWeek);
            endOfWeek = DateService.getEndOfWeek(startOfWeek);
        }

        Integer marksMonth;
        if(request.getParameter("marks_month") == null) {
            Date date = new Date();
            marksMonth = DateService.toMonth(date);
        } else {
            marksMonth = Integer.parseInt(request.getParameter("marks_month"));
        }

        List<Subject> subjects = studentInformationGetter.getSubjects();

        switch (category) {
            case TIMETABLE_CATEGORY:{
                Map<Integer, Map<Integer, Lesson>> lessons = studentInformationGetter.createLessonMap();
                request.setAttribute("lessons", lessons);
                request.setAttribute("category", TIMETABLE_CATEGORY);
                request.getRequestDispatcher("/WEB-INF/views/student/student_timetable.jsp").forward(request, response);
                break;
            }
            case MARKS_CATEGORY:{
                List<Mark> marks = studentInformationGetter.getMarks(marksMonth);
                Map<Subject, Map<Integer, Mark>> markMap = new HashMap<>();

                markMap = MarkMapCreator.create(marks, subjects);
                request.setAttribute("marks_month", marksMonth);
                request.setAttribute("marks_by_month", markMap);
                request.setAttribute("category", MARKS_CATEGORY);
                request.setAttribute("month_days_number", DateService.getNumberOfDays(marksMonth));
                request.getRequestDispatcher("/WEB-INF/views/student/student_marks.jsp").forward(request, response);
                break;
            }
            case HOMEWORK_CATEGORY:{
                List<Lesson> lessons = studentInformationGetter.getLessons();
                List<Homework> tasks = studentInformationGetter.getHomework(homeworkWeek);

                Map<Integer, Map<Lesson, Homework>> homework = LessonHomeworkMapCreator.create(lessons, tasks);
                request.setAttribute("homework", homework);
                request.setAttribute("homework_week", homeworkWeek);
                request.setAttribute("start_of_week", startOfWeek);
                request.setAttribute("end_of_week", endOfWeek);
                request.setAttribute("category", HOMEWORK_CATEGORY);
                request.getRequestDispatcher("/WEB-INF/views/student/student_homework.jsp").forward(request, response);
                break;
            }
            case STATISTICS_CATEGORY:{
                request.setAttribute("averages", studentInformationGetter.getAverageScores());
                request.setAttribute("category", STATISTICS_CATEGORY);

                request.getRequestDispatcher("/WEB-INF/views/student/student_statistics.jsp").forward(request, response);
                break;
            }
        }
    }
}
