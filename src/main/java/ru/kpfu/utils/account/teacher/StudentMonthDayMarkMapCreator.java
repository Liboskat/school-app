package ru.kpfu.utils.account.teacher;

import ru.kpfu.entities.Mark;
import ru.kpfu.entities.Student;
import ru.kpfu.utils.time.DateService;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ильшат on 14.11.2017.
 */
public class StudentMonthDayMarkMapCreator {
    public static Map<Student, Map<Integer, Mark>> create(List<Mark> marksOfMonth, List<Student> students) throws ParseException {
        Map<Student, Map<Integer, Mark>> map = new HashMap<>();
        for(Student student : students) {
            Map<Integer, Mark> marks = new HashMap<>();
            for (Mark m : marksOfMonth) {
                if(m.getStudent().getId().equals(student.getId())) {
                    int day = DateService.toMonthDay(m.getDate());
                    marks.put(day, m);
                }
            }
            map.put(student, marks);
        }
        return map;
    }
}
