package ru.kpfu.utils.validators;

import ru.kpfu.database.repositories.user.UserDbRepository;
import ru.kpfu.database.repositories.user.UserRepository;
import ru.kpfu.entities.User;
import ru.kpfu.exceptions.authentication.WrongEnteredUserDataException;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.utils.security.JasyptTextEncryptor;
import ru.kpfu.utils.security.TextEncryptor;

/**
 * Created by Ильшат on 01.11.2017.
 */
public class LoginValidator {
    public static void checkUserData(String login, String password) throws WrongEnteredUserDataException, DbException {
        TextEncryptor encryptor = new JasyptTextEncryptor();

        UserRepository repository = new UserDbRepository();
        User user = repository.findByLogin(login);

        if(user == null) {
            throw new WrongEnteredUserDataException("Login not found");
        }

        String reqPassword = encryptor.decrypt(user.getPassword());

        if(!password.equals(reqPassword)) {
            throw new WrongEnteredUserDataException("Passwords don't match");
        }
    }
}
