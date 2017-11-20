package ru.kpfu.utils.validators;

import ru.kpfu.database.repositories.user.UserDbRepository;
import ru.kpfu.entities.User;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.exceptions.registration.InviteNotFoundException;
import ru.kpfu.exceptions.registration.LoginIsInUseException;
import ru.kpfu.utils.security.JasyptTextEncryptor;
import ru.kpfu.utils.security.TextEncryptor;

/**
 * Created by Ильшат on 04.11.2017.
 */
public class RegistrationValidator {
    public static void checkInviteCode(String inviteCode) throws InviteNotFoundException, DbException {
        UserDbRepository repository = new UserDbRepository();
        User user = repository.findByInviteCode(inviteCode);
        if(user == null) {
            throw new InviteNotFoundException("Invite code " + inviteCode + " not found");
        }
    }

    public static void checkLogin(String login) throws LoginIsInUseException, DbException {
        UserDbRepository repository = new UserDbRepository();
        User user = repository.findByLogin(login);
        if(user != null) {
            throw new LoginIsInUseException("Login " + login + " is already in use");
        }
    }
}
