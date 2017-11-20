package ru.kpfu.database.repositories.user;

import ru.kpfu.database.repositories.CrudRepository;
import ru.kpfu.entities.User;
import ru.kpfu.exceptions.database.DbException;

/**
 * Created by Ильшат on 01.11.2017.
 */
public interface UserRepository extends CrudRepository<User> {
    User findByLogin(String encryptedLogin) throws DbException;
    User findByInviteCode(String inviteCode) throws DbException;
}
