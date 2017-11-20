package ru.kpfu.exceptions.registration;

/**
 * Created by Ильшат on 04.11.2017.
 */
public class PasswordsNotEqualException extends Exception{
    public PasswordsNotEqualException() {
        super();
    }

    public PasswordsNotEqualException(String message) {
        super(message);
    }
}
