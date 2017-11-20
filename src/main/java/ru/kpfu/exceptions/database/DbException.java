package ru.kpfu.exceptions.database;

/**
 * Created by Ильшат on 11.11.2017.
 */
public class DbException extends Exception {
    public DbException() {
        super();
    }

    public DbException(String message) {
        super(message);
    }
}
