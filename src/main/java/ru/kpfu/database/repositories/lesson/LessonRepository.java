package ru.kpfu.database.repositories.lesson;

import ru.kpfu.database.repositories.CrudRepository;
import ru.kpfu.entities.*;
import ru.kpfu.exceptions.database.DbException;

import java.util.List;

/**
 * Created by Ильшат on 05.11.2017.
 */
public interface LessonRepository extends CrudRepository<Lesson> {
    List<Lesson> findByGrade(Grade grade) throws DbException;
    List<Lesson> findByTeacher(Teacher teacher) throws DbException;
    Lesson findByNumberAndWeekdayAndGrade(Integer serialNumber, Integer weekday, Grade grade) throws DbException;
    Lesson findByNumberAndWeekdayAndTeacher(Integer serialNumber, Integer weekday, Teacher teacher) throws DbException;
    List<Lesson> findByWeekdayAndGradeAndSubjectAndTeacher(Integer weekday, Grade grade, Subject subject, Teacher teacher) throws DbException;
}
