package ru.kpfu.database.repositories.homework;

import ru.kpfu.database.repositories.CrudRepository;
import ru.kpfu.entities.*;
import ru.kpfu.exceptions.database.DbException;

import java.util.Date;
import java.util.List;

/**
 * Created by Ильшат on 08.11.2017.
 */
public interface HomeworkRepository extends CrudRepository<Homework>{
    List<Homework> findByGrade(Grade grade) throws DbException;
    List<Homework> findByGradeAndTeacherAndSubject(Grade grade, Teacher teacher, Subject subject) throws DbException;
    List<Homework> findByGradeAndWeek(Grade grade, Integer week) throws DbException;
    Homework findByGradeAndTeacherAndSubjectAndDate(Grade grade, Teacher teacher, Subject subject, Date date) throws DbException;
}
