package ru.kpfu.utils.account.student;

import ru.kpfu.entities.Homework;
import ru.kpfu.entities.Lesson;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ильшат on 13.11.2017.
 */
public class LessonHomeworkMapCreator {
    public static Map<Integer, Map<Lesson, Homework>> create(List<Lesson> lessons, List<Homework> tasks) {
        Collections.sort(lessons);

        Map<Integer, Map<Lesson, Homework>> map = new HashMap<>();

        for(Lesson lesson : lessons) {
            for(Homework task : tasks) {
                if(task.getLesson().getId().equals(lesson.getId())) {
                    Map<Lesson, Homework> lessonHomeworkMap = map.get(lesson.getWeekday());
                    if(lessonHomeworkMap == null) {
                        lessonHomeworkMap = new HashMap<>();
                    }
                    lessonHomeworkMap.put(lesson, task);
                    map.put(lesson.getWeekday(), lessonHomeworkMap);
                }
            }
            if(map.get(lesson.getWeekday()) == null) {
                map.put(lesson.getWeekday(), null);
            }
        }
        return map;
    }
}
