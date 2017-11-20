package ru.kpfu.utils.account.teacher;

import ru.kpfu.entities.Teacher;
import ru.kpfu.exceptions.authentication.UserSessionNotFoundException;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.utils.security.UserSessionInteractor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Ильшат on 08.11.2017.
 */
public class TeacherPostService {
    public static void doPost(HttpServletRequest request, HttpServletResponse response) throws ParseException, DbException, IOException {
        String login;
        try {
            login = UserSessionInteractor.getUser(request);
        } catch (UserSessionNotFoundException e) {
            response.sendRedirect("/app/login");
            return;
        }

        HomeworkService homeworkService = new HomeworkServiceImpl();
        MarkService markService = new MarkServiceImpl();

        HttpSession session = request.getSession();

        TeacherInformationGetterImpl teacherInformationGetter = new TeacherInformationGetterImpl(login);
        Teacher teacher = teacherInformationGetter.getTeacher();

        String currentGradeStr = request.getParameter("current_grade");
        String currentSubjectStr = request.getParameter("current_subject");
        String getHomeworkDateStr = request.getParameter("get_homework_date");

        session.setAttribute("current_grade", currentGradeStr);
        session.setAttribute("current_subject", currentSubjectStr);

        Date getHomeworkDate = null;
        if((getHomeworkDateStr != null) && !getHomeworkDateStr.isEmpty()) {
            getHomeworkDate = new Date(Long.parseLong(request.getParameter("get_homework_date")));
        }
        session.setAttribute("get_homework_date", getHomeworkDate);

        String saveHomeworkText = request.getParameter("save_homework_text");

        String updateHomeworkIdStr = request.getParameter("update_homework_id");
        String updateHomeworkText = request.getParameter("update_homework_text");

        String deleteHomeworkIdStr = request.getParameter("delete_homework_id");

        String setMarkStudentIdStr = request.getParameter("set_mark_student");
        String setMarkDateStr = request.getParameter("set_mark_date");

        Date setMarkDate = null;
        if((setMarkDateStr != null) && (!setMarkDateStr.isEmpty())) {
            setMarkDate = new Date(Long.parseLong(setMarkDateStr));
        }
        String setMarkValue = request.getParameter("set_mark_value");

        if((currentGradeStr != null) && (currentSubjectStr != null)) {
            if((saveHomeworkText != null) && (getHomeworkDate != null)) {
                homeworkService.save(teacher, Long.parseLong(currentGradeStr), Long.parseLong(currentSubjectStr), saveHomeworkText, getHomeworkDate);
                session.setAttribute("category", "marks");
            }
            if ((updateHomeworkIdStr != null) && (updateHomeworkText != null)) {
                homeworkService.update(Long.parseLong(updateHomeworkIdStr), updateHomeworkText);
                session.setAttribute("category", "marks");
            }
            if (deleteHomeworkIdStr != null) {
                homeworkService.delete(Long.parseLong(deleteHomeworkIdStr));
                session.setAttribute("category", "marks");
            }
            if((setMarkStudentIdStr != null) && (setMarkDate != null) && (setMarkValue != null)) {
                markService.save(Long.parseLong(currentSubjectStr), Long.parseLong(setMarkStudentIdStr), setMarkDate,
                        Integer.parseInt(setMarkValue), teacher);
                session.setAttribute("category", "marks");
            }
        }
    }
}
