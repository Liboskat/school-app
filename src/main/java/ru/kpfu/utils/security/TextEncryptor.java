package ru.kpfu.utils.security;

/**
 * Created by Ильшат on 01.11.2017.
 */
public interface TextEncryptor {
    String encrypt(String text);
    String decrypt(String text);
}
