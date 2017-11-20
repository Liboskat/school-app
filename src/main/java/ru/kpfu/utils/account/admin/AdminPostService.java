package ru.kpfu.utils.account.admin;

import ru.kpfu.database.repositories.grade.GradeDbRepository;
import ru.kpfu.database.repositories.grade.GradeRepository;
import ru.kpfu.entities.Grade;
import ru.kpfu.exceptions.database.DbException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Ильшат on 08.11.2017.
 */
public class AdminPostService {
    public static void doPost(HttpServletRequest request) throws IOException, DbException {
        InviteCodeGenerator inviteCodeGenerator = new InviteCodeGeneratorImpl();

        String gradeStr = request.getParameter("grade");
        String studentName = request.getParameter("student_name");
        String studentSurname = request.getParameter("student_surname");
        String teacherName = request.getParameter("teacher_name");

        if((gradeStr != null) && (studentName != null) && (studentSurname != null)) {
            Long gradeId = Long.parseLong(gradeStr);
            GradeRepository gradeRepository = new GradeDbRepository();
            Grade grade = gradeRepository.find(gradeId);
            String inviteCode = inviteCodeGenerator.generate();
            StudentSaveService studentSaveService = new StudentSaveServiceImpl();
            studentSaveService.save(grade, studentName, studentSurname, inviteCode);

            HttpSession session = request.getSession();
            session.setAttribute("category", "invite_student");
            session.setAttribute("invite_code", inviteCode);
        }

        if(teacherName != null) {
            String inviteCode = inviteCodeGenerator.generate();
            TeacherSaveService teacherSaveService = new TeacherSaveServiceImpl();
            teacherSaveService.save(teacherName, inviteCode);

            HttpSession session = request.getSession();
            session.setAttribute("category", "invite_teacher");
            session.setAttribute("invite_code", inviteCode);
        }
    }
}
