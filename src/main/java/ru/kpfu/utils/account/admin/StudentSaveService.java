package ru.kpfu.utils.account.admin;

import ru.kpfu.entities.Grade;
import ru.kpfu.exceptions.database.DbException;

/**
 * Created by Ильшат on 20.11.2017.
 */
public interface StudentSaveService {
    void save(Grade grade, String name, String surname, String invite) throws DbException;
}
