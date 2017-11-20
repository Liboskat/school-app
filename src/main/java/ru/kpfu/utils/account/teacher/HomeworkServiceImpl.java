package ru.kpfu.utils.account.teacher;

import ru.kpfu.database.repositories.grade.GradeDbRepository;
import ru.kpfu.database.repositories.grade.GradeRepository;
import ru.kpfu.database.repositories.homework.HomeworkDbRepository;
import ru.kpfu.database.repositories.homework.HomeworkRepository;
import ru.kpfu.database.repositories.lesson.LessonDbRepository;
import ru.kpfu.database.repositories.lesson.LessonRepository;
import ru.kpfu.database.repositories.subject.SubjectDbRepository;
import ru.kpfu.database.repositories.subject.SubjectRepository;
import ru.kpfu.entities.*;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.utils.time.DateService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by Ильшат on 08.11.2017.
 */
public class HomeworkServiceImpl implements HomeworkService{
    public void save(Teacher teacher, Long gradeId, Long subjectId, String text, Date date) throws ParseException, DbException {
        GradeRepository gradeRepository = new GradeDbRepository();
        Grade grade = gradeRepository.find(gradeId);

        SubjectRepository subjectRepository = new SubjectDbRepository();
        Subject subject = subjectRepository.find(subjectId);

        Integer weekday = DateService.toWeekday(date);

        LessonRepository lessonRepository = new LessonDbRepository();
        List<Lesson> lessons = lessonRepository.findByWeekdayAndGradeAndSubjectAndTeacher(weekday, grade, subject, teacher);

        if(lessons.size() == 0) {
            return;
        }

        Lesson lesson = lessons.get(0);

        Homework homework = Homework.builder()
                .date(date)
                .task(text)
                .date(date)
                .lesson(lesson)
                .build();
        HomeworkRepository homeworkRepository = new HomeworkDbRepository();
        homeworkRepository.save(homework);
    }

    public void update(Long homeworkId, String text) throws DbException {
        HomeworkRepository homeworkRepository = new HomeworkDbRepository();
        Homework homework = homeworkRepository.find(homeworkId);
        homework.setTask(text);
        homeworkRepository.update(homework);
    }

    public void delete(Long homeworkId) throws DbException {
        HomeworkRepository homeworkRepository = new HomeworkDbRepository();
        homeworkRepository.delete(homeworkId);
    }
}
