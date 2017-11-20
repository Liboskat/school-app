package ru.kpfu.utils.account;

import ru.kpfu.database.repositories.user.UserDbRepository;
import ru.kpfu.database.repositories.user.UserRepository;
import ru.kpfu.entities.User;
import ru.kpfu.exceptions.authentication.RoleNotFoundException;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.utils.security.JasyptTextEncryptor;
import ru.kpfu.utils.security.TextEncryptor;

/**
 * Created by Ильшат on 08.11.2017.
 */
public class UserRoleGetter {
    public static final String STUDENT_ROLE = "student";
    public static final String TEACHER_ROLE = "teacher";
    public static final String ADMIN_ROLE = "admin";

    public static String getRole(String login) throws DbException, RoleNotFoundException {
        UserRepository repository = new UserDbRepository();
        User user = repository.findByLogin(login);
        if(user == null) {
            throw new RoleNotFoundException();
        }
        return user.getRole();
    }
}
