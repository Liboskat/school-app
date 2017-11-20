package ru.kpfu.utils.account.admin;

import ru.kpfu.database.repositories.grade.GradeDbRepository;
import ru.kpfu.database.repositories.grade.GradeRepository;
import ru.kpfu.entities.Grade;
import ru.kpfu.exceptions.database.DbException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ильшат on 08.11.2017.
 */
public class AdminGetService {
    public static void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException {
        HttpSession session = request.getSession(false);
        if((session != null) && (session.getAttribute("invite_code") != null)) {
            request.setAttribute("invite_code", session.getAttribute("invite_code"));
            session.removeAttribute("invite_code");
        }
        String category;
        category = request.getParameter("category");
        if((session != null) && (session.getAttribute("category") != null)) {
            category = (String) session.getAttribute("category");
            session.removeAttribute("category");
        }

        if(category == null) {
            category = "invite_student";
        }
        switch (category) {
            case "invite_student": {
                GradeRepository gradeRepository = new GradeDbRepository();
                List<Grade> grades = gradeRepository.findAll();
                Collections.sort(grades);
                request.setAttribute("grades", grades);
                request.setAttribute("category", category);
                request.getRequestDispatcher("/WEB-INF/views/admin/admin_invite_student.jsp").forward(request, response);
                break;
            }
            case "invite_teacher": {
                request.setAttribute("category", category);
                request.getRequestDispatcher("/WEB-INF/views/admin/admin_invite_teacher.jsp").forward(request, response);
                break;
            }
        }
    }
}
