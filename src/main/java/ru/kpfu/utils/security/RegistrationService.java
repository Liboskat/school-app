package ru.kpfu.utils.security;

import ru.kpfu.database.repositories.user.UserDbRepository;
import ru.kpfu.database.repositories.user.UserRepository;
import ru.kpfu.entities.User;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.exceptions.registration.InviteNotFoundException;
import ru.kpfu.exceptions.registration.LoginIsInUseException;
import ru.kpfu.exceptions.registration.PasswordsNotEqualException;
import ru.kpfu.utils.validators.RegistrationValidator;

/**
 * Created by Ильшат on 04.11.2017.
 */
public class RegistrationService {
    public static void register(String login, String password, String passwordRepeat, String invite)
            throws InviteNotFoundException, LoginIsInUseException, PasswordsNotEqualException, DbException {

        RegistrationValidator.checkInviteCode(invite);
        RegistrationValidator.checkLogin(login);

        if(!password.equals(passwordRepeat)) {
            throw new PasswordsNotEqualException("Пароли не совпадают");
        }

        UserRepository userRepository = new UserDbRepository();
        User user = userRepository.findByInviteCode(invite);

        TextEncryptor encryptor = new JasyptTextEncryptor();
        user.setLogin(login);
        user.setPassword(encryptor.encrypt(password));

        userRepository.update(user);
    }
}
