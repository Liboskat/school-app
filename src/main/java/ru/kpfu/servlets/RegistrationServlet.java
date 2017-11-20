package ru.kpfu.servlets;

import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.exceptions.registration.InviteNotFoundException;
import ru.kpfu.exceptions.registration.LoginIsInUseException;
import ru.kpfu.exceptions.registration.PasswordsNotEqualException;
import ru.kpfu.utils.security.RegistrationService;
import ru.kpfu.utils.validators.RegistrationValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Ильшат on 04.11.2017.
 */
@WebServlet(name = "RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if((session != null) && (session.getAttribute("error") != null)) {
            request.setAttribute("error", session.getAttribute("error"));
            session.removeAttribute("error");
        }
        request.getRequestDispatcher("/WEB-INF/views/main/registration.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("repeat");
        String invite = request.getParameter("invite");

        try {
            RegistrationService.register(login, password, passwordRepeat, invite);
        } catch (InviteNotFoundException e) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Пригласительный код недействителен");
            response.sendRedirect("/app/registration");
            e.printStackTrace();
            return;
        } catch (LoginIsInUseException e) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Логин уже занят");
            response.sendRedirect("/app/registration");
            e.printStackTrace();
            return;
        } catch (PasswordsNotEqualException e) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Введенные пароли не совпадают");
            response.sendRedirect("/app/registration");
            e.printStackTrace();
            return;
        } catch (DbException e) {
            e.printStackTrace();
        }

        response.sendRedirect("/app/login");
    }
}
