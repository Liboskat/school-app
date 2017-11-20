package ru.kpfu.exceptions.authentication;

/**
 * Created by Ильшат on 01.11.2017.
 */
public class WrongEnteredUserDataException extends Exception {
    public WrongEnteredUserDataException() {
        super();
    }

    public WrongEnteredUserDataException(String message) {
        super(message);
    }
}
