package ru.kpfu.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.Years;

import java.util.List;

/**
 * Created by Ильшат on 05.11.2017.
 */
@Getter
@Setter
@Builder
public class Grade implements Comparable<Grade>{
    private Long id;
    private Integer start_year;
    private Integer letter;
    private List<Student> students;

    public void addStudent(Student s) {
        students.add(s);
    }

    @Override
    public String toString() {
        DateTime start = new DateTime(start_year, 9, 1, 0, 0, 0, 0);
        DateTime end = new DateTime();
        Years years = Years.yearsBetween(start, end);
        Integer grade_number = years.getYears() + 1;

        char symbol = (char)(letter - 1 + 'а');
        String string = grade_number + "" + symbol;
        return string;
    }

    @Override
    public int compareTo(Grade o) {
        if(!start_year.equals(o.getStart_year())) {
            return start_year.compareTo(o.getStart_year()) * -1;
        }
        else {
            return letter.compareTo(o.getLetter());
        }
    }
}
