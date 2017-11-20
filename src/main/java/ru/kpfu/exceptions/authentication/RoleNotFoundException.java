package ru.kpfu.exceptions.authentication;

/**
 * Created by Ильшат on 17.11.2017.
 */
public class RoleNotFoundException extends Exception {
    public RoleNotFoundException() {
        super();
    }

    public RoleNotFoundException(String message) {
        super(message);
    }
}
