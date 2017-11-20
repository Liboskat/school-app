package ru.kpfu.utils.account.teacher;

import ru.kpfu.database.repositories.grade.GradeDbRepository;
import ru.kpfu.database.repositories.grade.GradeRepository;
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
import ru.kpfu.database.repositories.teacher.TeacherDbRepository;
import ru.kpfu.database.repositories.teacher.TeacherRepository;
import ru.kpfu.entities.*;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.utils.account.student.StudentInformationGetter;
import ru.kpfu.utils.time.DateService;

import java.util.*;

/**
 * Created by Ильшат on 08.11.2017.
 */
public class TeacherInformationGetterImpl implements TeacherInformationGetter{
    private Teacher teacher;

    public TeacherInformationGetterImpl(String login) throws DbException {
        TeacherRepository teacherRepository = new TeacherDbRepository();
        teacher = teacherRepository.findByLogin(login);
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public List<Lesson> getLessons() throws DbException {
        LessonRepository lessonRepository = new LessonDbRepository();
        List<Lesson> lessons = lessonRepository.findByTeacher(teacher);
        return lessons;
    }

    public List<Grade> getGrades() throws DbException {
        GradeRepository gradeRepository = new GradeDbRepository();
        List<Grade> grades = gradeRepository.findByTeacher(teacher);
        return grades;
    }

    public List<Subject> getSubjects(Long gradeId) throws DbException {
        GradeRepository gradeRepository = new GradeDbRepository();
        Grade grade = gradeRepository.find(gradeId);

        SubjectRepository subjectRepository = new SubjectDbRepository();
        List<Subject> subjects = subjectRepository.findByTeacherAndGrade(teacher, grade);
        return subjects;
    }

    public Homework getHomework(Long gradeId, Long subjectId, Date date) throws DbException {
        GradeRepository gradeRepository = new GradeDbRepository();
        Grade grade = gradeRepository.find(gradeId);

        SubjectRepository subjectRepository = new SubjectDbRepository();
        Subject subject = subjectRepository.find(subjectId);

        HomeworkRepository homeworkRepository = new HomeworkDbRepository();
        Homework homework = homeworkRepository.findByGradeAndTeacherAndSubjectAndDate(grade, teacher, subject, date);
        return homework;
    }

    public List<Mark> getMarks(Long subjectId, Long gradeId, Integer monthNumber) throws DbException {
        GradeRepository gradeRepository = new GradeDbRepository();
        Grade grade = gradeRepository.find(gradeId);

        SubjectRepository subjectRepository = new SubjectDbRepository();
        Subject subject = subjectRepository.find(subjectId);

        MarkRepository markRepository = new MarkDbRepository();
        List<Mark> marks = markRepository.findBySubjectAndTeacherAndGradeAndMonth(subject, teacher, grade, monthNumber);
        return marks;
    }

    public List<Student> getStudents(Long gradeId) throws DbException {
        GradeRepository gradeRepository = new GradeDbRepository();
        Grade grade = gradeRepository.find(gradeId);

        StudentRepository studentRepository = new StudentDbRepository();
        List<Student> students = studentRepository.findByGrade(grade);
        return students;
    }

    public Set<Date> getAllDates(List<Lesson> lessons, Integer month) {
        Set<Date> set = new TreeSet<>();

        int year = DateService.getCurrentYear();
        month = month - 1;
        Calendar cal = new GregorianCalendar(year, month, 1);

        Set<Integer> daysOfMonth = getAllDaysOfWeek(lessons);
        do {
            // get the day of the week for the current day
            int day = cal.get(Calendar.DAY_OF_WEEK);
            // check if it is a Saturday or Sunday
            if (checkDayOfMonth(daysOfMonth, day)) {
                // print the day - but you could add them to a list or whatever
                set.add(cal.getTime());
            }
            // advance to the next day
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }  while (cal.get(Calendar.MONTH) == month);
        // stop when we reach the start of the next month
        return set;
    }

    private Set<Integer> getAllDaysOfWeek(List<Lesson> lessons) {
        Set<Integer> set = new HashSet<>();
        for(Lesson lesson : lessons) {
            set.add(lesson.getWeekday());
        }
        return set;
    }

    private boolean checkDayOfMonth(Set<Integer> daysOfWeek, Integer day) {
        for (Integer x : daysOfWeek) {
            if(x.equals(day)) {
                return true;
            }
        }
        return false;
    }

    public Map<Integer, Map<Integer, Lesson>> createLessonMap() throws DbException {
        LessonRepository lessonRepository = new LessonDbRepository();
        Map<Integer, Map<Integer, Lesson>> map = new HashMap<>();
        for(int lessonNum = 1; lessonNum < 9; lessonNum++) {
            Map<Integer, Lesson> numberMap = new HashMap<>();
            for(int weekDay = 1; weekDay < 7; weekDay++) {
                Lesson lesson = lessonRepository.findByNumberAndWeekdayAndTeacher(lessonNum, weekDay, teacher);
                numberMap.put(weekDay, lesson);
            }
            map.put(lessonNum, numberMap);
        }
        return map;
    }
}
