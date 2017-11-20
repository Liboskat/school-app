package ru.kpfu.servlets;

import ru.kpfu.exceptions.authentication.RoleNotFoundException;
import ru.kpfu.exceptions.authentication.UserSessionNotFoundException;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.utils.account.admin.AdminGetService;
import ru.kpfu.utils.account.admin.AdminPostService;
import ru.kpfu.utils.account.teacher.TeacherGetService;
import ru.kpfu.utils.account.teacher.TeacherPostService;
import ru.kpfu.utils.security.UserSessionInteractor;
import ru.kpfu.utils.account.student.StudentGetService;
import ru.kpfu.utils.account.UserRoleGetter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Ильшат on 08.11.2017.
 */
@WebServlet(name = "AccountServlet")
public class AccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login;
        try {
            login = UserSessionInteractor.getUser(request);
        } catch (UserSessionNotFoundException e) {
            response.sendRedirect("/app/login");
            return;
        }

        String role = null;
        try {
            role = UserRoleGetter.getRole(login);
        } catch (RoleNotFoundException| NullPointerException | DbException e) {
            response.sendRedirect("/app/login");
            return;
        }

        switch (role){
            case UserRoleGetter.STUDENT_ROLE:{
                break;
            }
            case UserRoleGetter.TEACHER_ROLE:{
                try {
                    TeacherPostService.doPost(request, response);
                } catch (DbException | ParseException e) {
                    response.sendRedirect("/app/error");
                }
                break;
            }
            case UserRoleGetter.ADMIN_ROLE:{
                try {
                    AdminPostService.doPost(request);
                } catch (DbException e) {
                    response.sendRedirect("/app/error");
                }
                break;
            }
        }

        response.sendRedirect("/app/account");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login;
        String role;
        try {
            login = UserSessionInteractor.getUser(request);
            role = UserRoleGetter.getRole(login);
        } catch (UserSessionNotFoundException | DbException | RoleNotFoundException e) {
            response.sendRedirect("/app/login");
            return;
        }


        switch (role){
            case UserRoleGetter.STUDENT_ROLE:{
                try {
                    StudentGetService.doGet(request, response);
                } catch (DbException | ParseException e) {
                    response.sendRedirect("/app/error");
                }
                break;
            }
            case UserRoleGetter.TEACHER_ROLE:{
                try {
                    TeacherGetService.doGet(request, response);
                } catch (DbException e) {
                    response.sendRedirect("/app/error");
                }
                break;
            }
            case UserRoleGetter.ADMIN_ROLE:{
                try {
                    AdminGetService.doGet(request, response);
                } catch (DbException e) {
                    response.sendRedirect("/app/error");
                }
                break;
            }
        }
    }
}
