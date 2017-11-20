package ru.kpfu.entities;

import lombok.*;

/**
 * Created by Ильшат on 05.11.2017.
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Comparable<Student> {
    private Long id;
    private String invite;
    private Grade grade;
    private String name;
    private String surname;


    @Override
    public int compareTo(Student o) {
        return (surname + name).compareTo(o.surname + o.name);
    }

    @Override
    public String toString() {
        return surname + " " + name;
    }
}
