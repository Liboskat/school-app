package ru.kpfu.utils.account.student;

import ru.kpfu.entities.Mark;
import ru.kpfu.entities.Subject;
import ru.kpfu.utils.time.DateService;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ильшат on 13.11.2017.
 */
public class MarkMapCreator {
    public static Map<Subject, Map<Integer, Mark>> create(List<Mark> marksOfMonth, List<Subject> subjects) throws ParseException {
        Map<Subject, Map<Integer, Mark>> map = new HashMap<>();
        for(Subject subject : subjects) {
            Map<Integer, Mark> marks = new HashMap<>();
            for (Mark m : marksOfMonth) {
                if(m.getLesson().getSubject().equals(subject)) {
                    int day = DateService.toMonthDay(m.getDate());
                    marks.put(day, m);
                }
            }
            map.put(subject, marks);
        }
        return map;
    }
}
