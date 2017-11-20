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
 * Created by Ильшат on 20.11.2017.
 */
public interface HomeworkService {
    void save(Teacher teacher, Long gradeId, Long subjectId, String text, Date date) throws ParseException, DbException;

    void update(Long homeworkId, String text) throws DbException;

    void delete(Long homeworkId) throws DbException;
}
