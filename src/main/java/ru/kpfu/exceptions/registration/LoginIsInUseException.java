package ru.kpfu.exceptions.registration;

/**
 * Created by Ильшат on 04.11.2017.
 */
public class LoginIsInUseException extends Exception{
    public LoginIsInUseException() {
        super();
    }

    public LoginIsInUseException(String message) {
        super(message);
    }
}
