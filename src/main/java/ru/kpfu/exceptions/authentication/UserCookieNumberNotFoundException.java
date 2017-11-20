package ru.kpfu.exceptions.authentication;

/**
 * Created by Ильшат on 04.11.2017.
 */
public class UserCookieNumberNotFoundException extends Exception{
    public UserCookieNumberNotFoundException() {
        super();
    }

    public UserCookieNumberNotFoundException(String message) {
        super(message);
    }
}
