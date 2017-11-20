package ru.kpfu.utils.account.teacher;

import ru.kpfu.database.repositories.lesson.LessonDbRepository;
import ru.kpfu.database.repositories.lesson.LessonRepository;
import ru.kpfu.database.repositories.mark.MarkDbRepository;
import ru.kpfu.database.repositories.mark.MarkRepository;
import ru.kpfu.database.repositories.student.StudentDbRepository;
import ru.kpfu.database.repositories.student.StudentRepository;
import ru.kpfu.database.repositories.subject.SubjectDbRepository;
import ru.kpfu.database.repositories.subject.SubjectRepository;
import ru.kpfu.entities.*;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.utils.time.DateService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by Ильшат on 09.11.2017.
 */
public class MarkServiceImpl implements MarkService{
    public void save(Long subjectId, Long studentId, Date date, Integer value, Teacher teacher) throws DbException, ParseException {
        StudentRepository studentRepository = new StudentDbRepository();
        Student student = studentRepository.find(studentId);

        SubjectRepository subjectRepository = new SubjectDbRepository();
        Subject subject = subjectRepository.find(subjectId);

        Integer weekday = DateService.toWeekday(date);
        LessonRepository lessonRepository = new LessonDbRepository();
        List<Lesson> lessons = lessonRepository.findByWeekdayAndGradeAndSubjectAndTeacher(weekday, student.getGrade(), subject, teacher);

        if (lessons.size() == 0) {
            return;
        }

        Lesson lesson = lessons.get(0);

        Mark mark = Mark.builder()
                .lesson(lesson)
                .student(student)
                .date(date)
                .value(value)
                .build();

        MarkRepository markRepository = new MarkDbRepository();
        markRepository.save(mark);
    }
}
