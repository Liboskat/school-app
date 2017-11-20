package ru.kpfu.entities;

import lombok.*;

/**
 * Created by Ильшат on 08.11.2017.
 */
@Setter
@Getter
@Builder
@EqualsAndHashCode
public class Subject implements Comparable<Subject>{
    private Long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Subject o) {
        return name.compareTo(o.getName());
    }
}
