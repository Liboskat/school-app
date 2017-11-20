package ru.kpfu.entities;

import lombok.*;

/**
 * Created by Ильшат on 05.11.2017.
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Lesson implements Comparable<Lesson>{
    private Long id;
    private Integer weekday;
    private Integer lessonNumber;
    private String startTime;
    private String endTime;
    private Grade grade;
    private Subject subject;
    private Teacher teacher;
    private Integer room;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Начало урока: ").append(startTime);
        sb.append("\r\nКонец урока: ").append(endTime);
        sb.append("\r\nУчитель: ").append(teacher.getName());
        sb.append("\r\nКабинет: ").append(room);
        sb.append("\r\nКласс: ").append(grade.toString());
        return sb.toString();
    }

    @Override
    public int compareTo(Lesson o) {
        if(weekday.equals(o.getWeekday())) {
            return lessonNumber.compareTo(o.lessonNumber);
        } else {
            return weekday.compareTo(o.weekday) * 10;
        }
    }
}
