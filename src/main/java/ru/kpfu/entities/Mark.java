package ru.kpfu.entities;

import lombok.*;

import java.util.Date;

/**
 * Created by Ильшат on 08.11.2017.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Mark {
    private Long id;
    private Student student;
    private Integer value;
    private Lesson lesson;
    private Date date;

    @Override
    public String toString() {
        return value.toString();
    }
}
