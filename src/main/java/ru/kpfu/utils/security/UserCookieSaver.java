package ru.kpfu.utils.security;

import ru.kpfu.database.repositories.usercookiepair.UserCookiePairDbRepository;
import ru.kpfu.database.repositories.usercookiepair.UserCookiePairRepository;
import ru.kpfu.entities.UserCookiePair;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.utils.cookies.CookieInteractor;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ильшат on 01.11.2017.
 */
public class UserCookieSaver {
    public static final String SEPARATOR = "|";
    public static final String USER_COOKIE_NAME = "user";

    public static void save(String login, HttpServletResponse response) throws DbException {
        Long number = RandomUserNumberGenerator.generate();
        UserCookiePairRepository repository = new UserCookiePairDbRepository();
        UserCookiePair userCookiePair = repository.findByLogin(login);
        if(userCookiePair == null) {
            userCookiePair = UserCookiePair.builder()
                    .login(login)
                    .number(number)
                    .build();
            repository.save(userCookiePair);
        }
        else {
            userCookiePair.setNumber(number);
            repository.update(userCookiePair);
        }
        String value = login + SEPARATOR + number;
        CookieInteractor.addCookie(response, USER_COOKIE_NAME, value, CookieInteractor.MONTH_AGE);
    }
}
