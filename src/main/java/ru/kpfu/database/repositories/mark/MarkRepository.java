package ru.kpfu.database.repositories.mark;


import ru.kpfu.database.repositories.CrudRepository;
import ru.kpfu.entities.*;
import ru.kpfu.exceptions.database.DbException;

import java.util.List;

/**
 * Created by Ильшат on 08.11.2017.
 */
public interface MarkRepository extends CrudRepository<Mark> {
    List<Mark> findByStudent(Student student) throws DbException;
    List<Mark> findByGradeAndTeacherAndSubject(Grade grade, Teacher teacher, Subject subject) throws DbException;
    List<Mark> findByStudentAndMonth(Student student, Integer monthNumber) throws DbException;
    List<Mark> findBySubjectAndTeacherAndGradeAndMonth(Subject subject, Teacher teacher, Grade grade, Integer monthNumber) throws DbException;
    List<Mark> findByStudentAndSubjectAndQuarter(Student student, Subject subject, Integer quarter) throws DbException;
    List<Mark> findByStudentAndSubject(Student student, Subject subject) throws DbException;
}
