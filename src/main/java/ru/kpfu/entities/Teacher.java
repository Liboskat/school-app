package ru.kpfu.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Ильшат on 08.11.2017.
 */
@Getter
@Setter
@Builder
@ToString
public class Teacher {
    private Long id;
    private String invite;
    private String name;
}
