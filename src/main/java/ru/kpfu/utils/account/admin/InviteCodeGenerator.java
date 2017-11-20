package ru.kpfu.utils.account.admin;

import ru.kpfu.exceptions.database.DbException;

/**
 * Created by Ильшат on 20.11.2017.
 */
public interface InviteCodeGenerator {
    String generate() throws DbException;
}
