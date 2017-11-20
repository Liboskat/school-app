package ru.kpfu.utils.account.teacher;

import ru.kpfu.entities.*;
import ru.kpfu.exceptions.database.DbException;

import java.util.*;

/**
 * Created by Ильшат on 20.11.2017.
 */
public interface TeacherInformationGetter {
    Teacher getTeacher();

    List<Lesson> getLessons() throws DbException;

    List<Grade> getGrades() throws DbException;

    List<Subject> getSubjects(Long gradeId) throws DbException;

    Homework getHomework(Long gradeId, Long subjectId, Date date) throws DbException;

    List<Mark> getMarks(Long subjectId, Long gradeId, Integer monthNumber) throws DbException;

    List<Student> getStudents(Long gradeId) throws DbException;

    Set<Date> getAllDates(List<Lesson> lessons, Integer month);

    Map<Integer, Map<Integer, Lesson>> createLessonMap() throws DbException;
}
