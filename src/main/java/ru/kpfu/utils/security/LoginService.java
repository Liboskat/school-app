package ru.kpfu.utils.security;

import ru.kpfu.exceptions.authentication.UserCookieNumberNotFoundException;
import ru.kpfu.exceptions.authentication.UserDataNotEnteredException;
import ru.kpfu.exceptions.authentication.UserSessionNotFoundException;
import ru.kpfu.exceptions.authentication.WrongUserCookieException;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.utils.cookies.CookieInteractor;
import ru.kpfu.utils.validators.UserCookieValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ильшат on 04.11.2017.
 */
public class LoginService {
    public static void login(HttpServletRequest req, HttpServletResponse resp) throws UserDataNotEnteredException, DbException, UserSessionNotFoundException {
        String sessionData = UserSessionInteractor.getUser(req);
        if(sessionData == null) {
            String cookie = CookieInteractor.getCookieValue(req, UserCookieSaver.USER_COOKIE_NAME);
            if(cookie != null) {
                try {
                    UserCookieValidator.checkUserCookie(cookie);
                } catch (UserCookieNumberNotFoundException | WrongUserCookieException e) {
                    CookieInteractor.removeCookie(resp, cookie);
                }
            } else {
                throw new UserDataNotEnteredException("User must write his email and password");
            }
        }
    }
}
