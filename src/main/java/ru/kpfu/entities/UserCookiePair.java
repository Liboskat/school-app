package ru.kpfu.entities;

import lombok.*;


/**
 * Created by Ильшат on 01.11.2017.
 */
@Getter
@Setter
@Builder
public class UserCookiePair {
    private String login;
    private Long number;
}
