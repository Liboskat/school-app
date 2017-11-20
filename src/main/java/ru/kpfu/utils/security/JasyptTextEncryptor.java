package ru.kpfu.utils.security;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Created by Ильшат on 01.11.2017.
 */
public class JasyptTextEncryptor implements TextEncryptor {
    private static final String PASSWORD = "o4twq24uo24tu";
    private BasicTextEncryptor encryptor;

    public JasyptTextEncryptor() {
        encryptor = new BasicTextEncryptor();
        encryptor.setPassword(PASSWORD);
    }

    public String encrypt(String text) {
        return encryptor.encrypt(text);
    }

    public String decrypt(String text) {
        return encryptor.decrypt(text);
    }
}
