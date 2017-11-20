package ru.kpfu.utils.account.teacher;

import ru.kpfu.entities.*;
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
import java.util.*;

/**
 * Created by Ильшат on 08.11.2017.
 */
public class TeacherGetService {
    private static final String TIMETABLE_CATEGORY = "timetable";
    private static final String MARKS_CATEGORY = "marks";

    public static void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException {
        String login;
        try {
            login = UserSessionInteractor.getUser(request);
        } catch (UserSessionNotFoundException e) {
            response.sendRedirect("/app/login");
            return;
        }

        TeacherInformationGetter teacherInformationGetter = new TeacherInformationGetterImpl(login);
        HttpSession session = request.getSession(false);

        String category = request.getParameter("category");
        if((session != null) && (session.getAttribute("category") != null)) {
            category = (String) session.getAttribute("category");
            session.removeAttribute("category");
        }
        if(category == null) {
            category = TIMETABLE_CATEGORY;
        }

        String currentGrade = request.getParameter("current_grade");
        if((session != null) && (session.getAttribute("current_grade") != null)) {
            currentGrade = (String) session.getAttribute("current_grade");
            session.removeAttribute("current_grade");
        }

        String currentSubject = request.getParameter("current_subject");
        if((session != null) && (session.getAttribute("current_subject") != null)) {
            currentSubject = (String) session.getAttribute("current_subject");
            session.removeAttribute("current_subject");
        }

        Integer marksMonth;
        if((request.getParameter("marks_month") == null) || request.getParameter("marks_month").isEmpty()) {
            Date date = new Date();
            marksMonth = DateService.toMonth(date);
        } else {
            marksMonth = Integer.parseInt(request.getParameter("marks_month"));
        }

        Date getHomeworkDate = null;
        if((request.getParameter("get_homework_date") != null) && (!request.getParameter("get_homework_date").isEmpty())) {
            getHomeworkDate = new Date(Long.parseLong(request.getParameter("get_homework_date")));
        } else if((session != null) && (session.getAttribute("get_homework_date") != null)) {
            getHomeworkDate = (Date) session.getAttribute("get_homework_date");
            session.removeAttribute("get_homework_date");
        }

        switch (category) {
            case TIMETABLE_CATEGORY: {
                Map<Integer, Map<Integer, Lesson>> lessons = teacherInformationGetter.createLessonMap();
                request.setAttribute("lessons", lessons);
                request.setAttribute("category", TIMETABLE_CATEGORY);
                request.getRequestDispatcher("/WEB-INF/views/teacher/teacher_timetable.jsp").forward(request, response);
                break;
            }
            case MARKS_CATEGORY: {
                List<Lesson> lessons = teacherInformationGetter.getLessons();
                List<Grade> grades = teacherInformationGetter.getGrades();
                Collections.sort(grades);
                List<Subject> subjects = new ArrayList<>();
                if ((currentGrade != null) && !currentGrade.isEmpty()) {
                    subjects = teacherInformationGetter.getSubjects(Long.parseLong(currentGrade));
                    Collections.sort(subjects);
                }

                Map<Student, Map<Integer, Mark>> markMap = new HashMap<>();


                List<Student> students = new ArrayList<>();
                if ((currentGrade != null)  && !currentGrade.isEmpty() && (currentSubject != null) && !currentSubject.isEmpty()) {
                    List<Mark> marks = teacherInformationGetter.getMarks(Long.parseLong(currentSubject), Long.parseLong(currentGrade), marksMonth);
                    students = teacherInformationGetter.getStudents(Long.parseLong(currentGrade));
                    try {
                        markMap = StudentMonthDayMarkMapCreator.create(marks, students);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                Homework homework = null;
                if((currentGrade != null) && !currentGrade.isEmpty() &&
                        (currentSubject != null) && !currentSubject.isEmpty() && (getHomeworkDate != null)) {
                    homework = teacherInformationGetter.getHomework(Long.parseLong(currentGrade), Long.parseLong(currentSubject),
                            getHomeworkDate);

                }
                Set<Date> allDates = teacherInformationGetter.getAllDates(lessons, marksMonth);

                request.setAttribute("found_homework", homework);
                request.setAttribute("dates", allDates);
                request.setAttribute("grades", grades);
                request.setAttribute("subjects", subjects);
                request.setAttribute("current_grade", currentGrade);
                request.setAttribute("current_subject", currentSubject);
                request.setAttribute("get_homework_date", getHomeworkDate);
                request.setAttribute("marks_month", marksMonth);
                request.setAttribute("month_days_number", DateService.getNumberOfDays(marksMonth));
                request.setAttribute("marks_by_month", markMap);
                request.setAttribute("students", students);
                request.setAttribute("category", MARKS_CATEGORY);
                request.getRequestDispatcher("/WEB-INF/views/teacher/teacher_marks.jsp").forward(request, response);
                break;
            }
        }
    }
}
