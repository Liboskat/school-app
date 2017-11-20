package ru.kpfu.exceptions.authentication;

/**
 * Created by Ильшат on 04.11.2017.
 */
public class UserDataNotEnteredException extends Exception {
    public UserDataNotEnteredException() {
        super();
    }

    public UserDataNotEnteredException(String message) {
        super(message);
    }
}
