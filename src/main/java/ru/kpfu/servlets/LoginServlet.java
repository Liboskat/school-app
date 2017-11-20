package ru.kpfu.servlets;

import ru.kpfu.database.repositories.user.UserDbRepository;
import ru.kpfu.entities.User;
import ru.kpfu.exceptions.authentication.UserDataNotEnteredException;
import ru.kpfu.exceptions.authentication.UserSessionNotFoundException;
import ru.kpfu.exceptions.authentication.WrongEnteredUserDataException;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.utils.security.LoginService;
import ru.kpfu.utils.security.UserSessionInteractor;
import ru.kpfu.utils.security.UserCookieSaver;
import ru.kpfu.utils.validators.LoginValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Ильшат on 01.11.2017.
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LoginService.login(req, resp);
        } catch (UserDataNotEnteredException e) {
            HttpSession session = req.getSession(false);
            if((session != null) && (session.getAttribute("error") != null)) {
                req.setAttribute("error", session.getAttribute("error"));
                session.removeAttribute("error");
            }
            req.getRequestDispatcher("/WEB-INF/views/main/login.jsp").forward(req, resp);
            return;
        } catch (DbException | UserSessionNotFoundException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("/app/account");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        boolean remember = "checked".equals(req.getParameter("remember"));

        try {
            LoginValidator.checkUserData(login, password);
        } catch (WrongEnteredUserDataException ex) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Данные введены неверно");
            resp.sendRedirect("/app/login");
            return;
        } catch (DbException e) {
            e.printStackTrace();
        }

        UserSessionInteractor.save(login, req);

        if(remember) {
            try {
                UserCookieSaver.save(login, resp);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }

        resp.sendRedirect("/app/account");
    }
}
