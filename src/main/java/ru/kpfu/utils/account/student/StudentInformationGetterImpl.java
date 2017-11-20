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

import java.util.*;

/**
 * Created by Ильшат on 08.11.2017.
 */
public class StudentInformationGetterImpl implements StudentInformationGetter{
    private Student student;

    public Student getStudent() {
        return student;
    }

    public StudentInformationGetterImpl(String login) throws DbException {
        StudentRepository studentRepository = new StudentDbRepository();
        student = studentRepository.findByLogin(login);
    }

    public List<Lesson> getLessons() throws DbException {
        LessonRepository lessonRepository = new LessonDbRepository();
        List<Lesson> lessons = lessonRepository.findByGrade(student.getGrade());
        return lessons;
    }

    public List<Mark> getMarks(Integer month) throws DbException {
        MarkRepository markRepository = new MarkDbRepository();
        List<Mark> marks = markRepository.findByStudentAndMonth(student, month);
        return marks;
    }

    public List<Homework> getHomework(Integer week) throws DbException {
        HomeworkRepository homeworkRepository = new HomeworkDbRepository();
        List<Homework> tasks = homeworkRepository.findByGradeAndWeek(student.getGrade(), week);
        return tasks;
    }

    public List<Subject> getSubjects() throws DbException {
        SubjectRepository subjectRepository = new SubjectDbRepository();
        List<Subject> subjects = subjectRepository.findByGrade(student.getGrade());
        return subjects;
    }

    public Map<Subject, Map<Integer, Double>> getAverageScores() throws DbException {
        MarkRepository markRepository = new MarkDbRepository();
        List<Subject> subjects = getSubjects();
        Map<Subject, Map<Integer, Double>> map = new HashMap<>();
        for(Subject subject : subjects) {
            Map<Integer, Double> quarterDoubleMap = new HashMap<>();
            for(int i = 1; i < 6; i++) {
                List<Mark> marks = new ArrayList<>();
                if(i == 5) {
                    marks = markRepository.findByStudentAndSubject(student, subject);
                } else {
                    marks = markRepository.findByStudentAndSubjectAndQuarter(student, subject, i);
                }
                Double average = countAverage(marks);
                quarterDoubleMap.put(i, average);
            }
            map.put(subject, quarterDoubleMap);
        }
        return map;
    }

    private Double countAverage(List<Mark> list){
        if(list.size() == 0) {
            return null;
        }
        int sum = 0;
        for (Mark mark : list) {
            sum += mark.getValue();
        }
        return (double) (sum / list.size());
    }

    public Map<Integer, Map<Integer, Lesson>> createLessonMap() throws DbException {
        LessonRepository lessonRepository = new LessonDbRepository();
        Map<Integer, Map<Integer, Lesson>> map = new HashMap<>();
        for(int lessonNum = 1; lessonNum < 9; lessonNum++) {
            Map<Integer, Lesson> numberMap = new HashMap<>();
            for(int weekDay = 1; weekDay < 7; weekDay++) {
                Lesson lesson = lessonRepository.findByNumberAndWeekdayAndGrade(lessonNum, weekDay, student.getGrade());
                numberMap.put(weekDay, lesson);
            }
            map.put(lessonNum, numberMap);
        }
        return map;
    }
}
