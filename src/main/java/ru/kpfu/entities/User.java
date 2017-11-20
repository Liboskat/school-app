package ru.kpfu.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Ильшат on 01.11.2017.
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class User {
    private Long id;
    private String invite;
    private String login;
    private String password;
    private String role;
}
