package ru.kpfu.database.repositories.usercookiepair;

import ru.kpfu.database.repositories.CrudRepository;
import ru.kpfu.entities.UserCookiePair;
import ru.kpfu.exceptions.database.DbException;

/**
 * Created by Ильшат on 01.11.2017.
 */
public interface UserCookiePairRepository extends CrudRepository<UserCookiePair> {
    UserCookiePair findByLogin(String login) throws DbException;
}
