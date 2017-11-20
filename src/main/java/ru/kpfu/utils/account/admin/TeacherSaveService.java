package ru.kpfu.utils.account.admin;

import ru.kpfu.exceptions.database.DbException;

/**
 * Created by Ильшат on 20.11.2017.
 */
public interface TeacherSaveService {
    void save(String name, String invite) throws DbException;
}
