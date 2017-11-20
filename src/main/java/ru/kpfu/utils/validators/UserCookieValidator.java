package ru.kpfu.utils.validators;

import ru.kpfu.database.repositories.usercookiepair.UserCookiePairDbRepository;
import ru.kpfu.database.repositories.usercookiepair.UserCookiePairRepository;
import ru.kpfu.entities.UserCookiePair;
import ru.kpfu.exceptions.authentication.UserCookieNumberNotFoundException;
import ru.kpfu.exceptions.authentication.WrongUserCookieException;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.utils.security.UserCookieSaver;

/**
 * Created by Ильшат on 04.11.2017.
 */
public class UserCookieValidator {
    public static void checkUserCookie(String cookie) throws UserCookieNumberNotFoundException, WrongUserCookieException, DbException {
        String[] cookiePairArr = cookie.split(UserCookieSaver.SEPARATOR);
        String userName = cookiePairArr[0];
        Long userNumber = Long.parseLong(cookiePairArr[1]);

        UserCookiePairRepository userCookiePairRepository = new UserCookiePairDbRepository();
        UserCookiePair pair = userCookiePairRepository.findByLogin(userName);
        if(pair == null) {
            throw new UserCookieNumberNotFoundException("User cookie number not found for " + userName);
        }
        else {
            if(!pair.getNumber().equals(userNumber)) {
                throw new WrongUserCookieException("Cookie number doesn't match with number in the table for " + userName);
            }
        }
    }
}
