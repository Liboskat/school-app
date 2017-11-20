package ru.kpfu.servlets;

import ru.kpfu.utils.cookies.CookieInteractor;
import ru.kpfu.utils.security.UserCookieSaver;
import ru.kpfu.utils.security.UserSessionInteractor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ильшат on 17.11.2017.
 */
@WebServlet(name = "LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CookieInteractor.removeCookie(response, UserCookieSaver.USER_COOKIE_NAME);
        UserSessionInteractor.remove(request);
        response.sendRedirect("/app/login");
    }
}
