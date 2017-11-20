package ru.kpfu.exceptions.registration;

/**
 * Created by Ильшат on 04.11.2017.
 */
public class InviteNotFoundException extends Exception {
    public InviteNotFoundException() {
        super();
    }

    public InviteNotFoundException(String message) {
        super(message);
    }
}
