package ru.kpfu.exceptions.authentication;

/**
 * Created by Ильшат on 04.11.2017.
 */
public class WrongUserCookieException extends Exception{
    public WrongUserCookieException() {
        super();
    }

    public WrongUserCookieException(String message) {
        super(message);
    }
}
