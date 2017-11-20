package ru.kpfu.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Created by Ильшат on 08.11.2017.
 */
@Builder
@Getter
@Setter
@ToString
public class Homework {
    private Long id;
    private Lesson lesson;
    private String task;
    private Date date;
}
