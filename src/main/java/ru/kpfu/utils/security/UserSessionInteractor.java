package ru.kpfu.utils.security;

import ru.kpfu.exceptions.authentication.UserSessionNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Ильшат on 04.11.2017.
 */
public class UserSessionInteractor {
    public static final String USER_SESSION = "user";

    public static void save(String login, HttpServletRequest request) {
        HttpSession session = request.getSession();
        TextEncryptor encryptor = new JasyptTextEncryptor();
        session.setAttribute(USER_SESSION ,encryptor.encrypt(login));
    }

    public static String getUser(HttpServletRequest request) throws UserSessionNotFoundException {
        HttpSession session = request.getSession();
        if(session == null) {
            throw new UserSessionNotFoundException("Session not found");
        }
        TextEncryptor encryptor = new JasyptTextEncryptor();
        String user = encryptor.decrypt((String)session.getAttribute(USER_SESSION));
        return user;
    }

    public static void remove(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(USER_SESSION);
    }
}
