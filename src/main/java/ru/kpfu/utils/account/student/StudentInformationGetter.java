package ru.kpfu.utils.account.student;

import ru.kpfu.database.repositories.homework.HomeworkDbRepository;
import ru.kpfu.database.repositories.homework.HomeworkRepository;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ильшат on 20.11.2017.
 */
public interface StudentInformationGetter {
    Student getStudent();

    List<Lesson> getLessons() throws DbException;

    List<Mark> getMarks(Integer month) throws DbException;

    List<Homework> getHomework(Integer week) throws DbException;

    List<Subject> getSubjects() throws DbException;

    Map<Subject, Map<Integer, Double>> getAverageScores() throws DbException;

    Map<Integer, Map<Integer, Lesson>> createLessonMap() throws DbException;
}
