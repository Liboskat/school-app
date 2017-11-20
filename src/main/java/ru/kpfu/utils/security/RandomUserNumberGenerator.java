package ru.kpfu.utils.security;

import java.util.Random;

/**
 * Created by Ильшат on 01.11.2017.
 */
public class RandomUserNumberGenerator {
    public static Long generate() {
        Long number = new Random().nextLong();
        return number;
    }
}
