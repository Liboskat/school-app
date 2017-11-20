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
 * Created by Ильшат on 20.11.2017.
 */
public interface MarkService {
    void save(Long subjectId, Long studentId, Date date, Integer value, Teacher teacher) throws DbException, ParseException;
}
