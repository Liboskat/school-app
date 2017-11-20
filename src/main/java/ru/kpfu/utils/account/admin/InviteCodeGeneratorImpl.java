package ru.kpfu.utils.account.admin;

import ru.kpfu.database.repositories.user.UserDbRepository;
import ru.kpfu.database.repositories.user.UserRepository;
import ru.kpfu.exceptions.database.DbException;

import java.nio.charset.Charset;
import java.util.Random;

/**
 * Created by Ильшат on 08.11.2017.
 */
public class InviteCodeGeneratorImpl implements InviteCodeGenerator{
    public String generate() throws DbException {
        String chars = "abcdefghijklmnopqrstuwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        while (code.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * chars.length());
            code.append(chars.charAt(index));
        }
        String saltStr = code.toString();
        return saltStr;
    }
}
