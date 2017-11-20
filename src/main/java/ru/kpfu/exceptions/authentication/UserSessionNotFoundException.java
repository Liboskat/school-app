package ru.kpfu.exceptions.authentication;

/**
 * Created by Ильшат on 17.11.2017.
 */
public class UserSessionNotFoundException extends Exception {
    public UserSessionNotFoundException() {
        super();
    }

    public UserSessionNotFoundException(String message) {
        super(message);
    }
}
