package ru.kpfu.database.repositories.lesson;

import org.apache.commons.dbutils.DbUtils;
import ru.kpfu.database.repositories.grade.GradeDbRepository;
import ru.kpfu.database.repositories.grade.GradeRepository;
import ru.kpfu.database.repositories.subject.SubjectDbRepository;
import ru.kpfu.database.repositories.subject.SubjectRepository;
import ru.kpfu.database.repositories.teacher.TeacherDbRepository;
import ru.kpfu.database.repositories.teacher.TeacherRepository;
import ru.kpfu.database.utils.DBWrapper;
import ru.kpfu.entities.*;
import ru.kpfu.exceptions.database.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ильшат on 05.11.2017.
 */
public class LessonDbRepository implements LessonRepository{
    private Connection connection;

    public static final String SELECT_BY_NUMBER_AND_WEEKDAY_AND_GRADE = "SELECT " +
            "id, subject_id, teacher_id, room, start_time, end_time " +
            "FROM timetable " +
            "LEFT JOIN lesson_time ON lesson_serial_number = lesson_time.lesson_number " +
            "WHERE lesson_serial_number = ? AND weekday = ? AND grade_id = ?";
    public static final String SELECT_BY_NUMBER_AND_WEEKDAY_AND_TEACHER = "SELECT " +
            "id, subject_id, grade_id, room, start_time, end_time " +
            "FROM timetable " +
            "LEFT JOIN lesson_time ON lesson_serial_number = lesson_time.lesson_number " +
            "WHERE lesson_serial_number = ? AND weekday = ? AND teacher_id = ?";
    public static final String SELECT_BY_TEACHER = "SELECT " +
            "id, weekday, lesson_serial_number, start_time, end_time, grade_id, subject_id, room " +
            "FROM timetable " +
            "LEFT JOIN lesson_time ON lesson_serial_number = lesson_time.lesson_number " +
            "WHERE teacher_id = ?";
    public static final String SELECT_BY_GRADE = "SELECT " +
            "id, weekday, lesson_serial_number, start_time, end_time, subject_id, teacher_id, room " +
            "FROM timetable " +
            "LEFT JOIN lesson_time ON lesson_serial_number = lesson_time.lesson_number " +
            "WHERE grade_id = ?";
    public static final String SELECT_BY_ID = "SELECT " +
            "weekday, lesson_serial_number, start_time, end_time, grade_id, subject_id, teacher_id, room " +
            "FROM timetable " +
            "LEFT JOIN lesson_time ON lesson_serial_number = lesson_time.lesson_number " +
            "WHERE id = ?";
    public static final String SELECT_BY_WEEKDAY_AND_GRADE_AND_SUBJECT_AND_TEACHER = "SELECT " +
            "id, room, lesson_serial_number, start_time, end_time " +
            "FROM timetable " +
            "LEFT JOIN lesson_time ON lesson_serial_number = lesson_time.lesson_number " +
            "WHERE weekday = ? AND grade_id = ? AND subject_id = ? AND teacher_id = ?";


    @Override
    public Lesson findByNumberAndWeekdayAndGrade(Integer serialNumber, Integer weekday, Grade grade) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Lesson> lessons = new ArrayList<>();
        Map<Long, Lesson> lessonsMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_NUMBER_AND_WEEKDAY_AND_GRADE);
            st.setInt(1, serialNumber);
            st.setInt(2, weekday);
            st.setLong(3, grade.getId());
            r = st.executeQuery();
            while(r.next()){
                SubjectRepository subjectRepository = new SubjectDbRepository();
                TeacherRepository teacherRepository = new TeacherDbRepository();
                Long id = r.getLong("id");
                Lesson lesson = lessonsMap.get(id);
                if(lesson == null) {
                    lesson = Lesson.builder()
                            .id(id)
                            .lessonNumber(serialNumber)
                            .grade(grade)
                            .weekday(weekday)
                            .room(r.getInt("room"))
                            .subject(subjectRepository.find(r.getLong("subject_id")))
                            .teacher(teacherRepository.find(r.getLong("teacher_id")))
                            .startTime(r.getString("start_time"))
                            .endTime(r.getString("end_time"))
                            .build();
                    lessons.add(lesson);
                    lessonsMap.put(id, lesson);
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            try{
                DbUtils.close(r);
                DbUtils.close(st);
                DbUtils.close(connection);
            } catch (SQLException ignored) {}
        }
        if(lessons.size() > 0) {
            return lessons.get(0);
        }
        return null;
    }

    @Override
    public Lesson findByNumberAndWeekdayAndTeacher(Integer serialNumber, Integer weekday, Teacher teacher) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Lesson> lessons = new ArrayList<>();
        Map<Long, Lesson> lessonsMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_NUMBER_AND_WEEKDAY_AND_TEACHER);
            st.setInt(1, serialNumber);
            st.setInt(2, weekday);
            st.setLong(3, teacher.getId());
            r = st.executeQuery();
            while(r.next()){
                SubjectRepository subjectRepository = new SubjectDbRepository();
                GradeRepository gradeRepository = new GradeDbRepository();
                Long id = r.getLong("id");
                Lesson lesson = lessonsMap.get(id);
                if(lesson == null) {
                    lesson = Lesson.builder()
                            .id(id)
                            .lessonNumber(serialNumber)
                            .grade(gradeRepository.find(r.getLong("grade_id")))
                            .weekday(weekday)
                            .room(r.getInt("room"))
                            .subject(subjectRepository.find(r.getLong("subject_id")))
                            .teacher(teacher)
                            .startTime(r.getString("start_time"))
                            .endTime(r.getString("end_time"))
                            .build();
                    lessons.add(lesson);
                    lessonsMap.put(id, lesson);
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            try{
                DbUtils.close(r);
                DbUtils.close(st);
                DbUtils.close(connection);
            } catch (SQLException ignored) {}
        }
        if(lessons.size() > 0) {
            return lessons.get(0);
        }
        return null;
    }

    @Override
    public List<Lesson> findByTeacher(Teacher teacher) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Lesson> lessons = new ArrayList<>();
        Map<Long, Lesson> lessonsMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_TEACHER);
            st.setLong(1, teacher.getId());
            r = st.executeQuery();
            while(r.next()){
                GradeRepository gradeRepository = new GradeDbRepository();
                SubjectRepository subjectRepository = new SubjectDbRepository();
                TeacherRepository teacherRepository = new TeacherDbRepository();
                Long id = r.getLong("id");
                Lesson lesson = lessonsMap.get(id);
                if(lesson == null) {
                    lesson = Lesson.builder()
                            .id(id)
                            .lessonNumber(r.getInt("lesson_serial_number"))
                            .grade(gradeRepository.find(r.getLong("grade_id")))
                            .weekday(r.getInt("weekday"))
                            .room(r.getInt("room"))
                            .subject(subjectRepository.find(r.getLong("subject_id")))
                            .teacher(teacherRepository.find(teacher.getId()))
                            .startTime(r.getString("start_time"))
                            .endTime(r.getString("end_time"))
                            .build();
                    lessons.add(lesson);
                    lessonsMap.put(id, lesson);
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            try{
                DbUtils.close(r);
                DbUtils.close(st);
                DbUtils.close(connection);
            } catch (SQLException ignored) {}
        }
        return lessons;
    }

    @Override
    public List<Lesson> findByGrade(Grade grade) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Lesson> lessons = new ArrayList<>();
        Map<Long, Lesson> lessonsMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_GRADE);
            st.setLong(1, grade.getId());
            r = st.executeQuery();
            while(r.next()){
                SubjectRepository subjectRepository = new SubjectDbRepository();
                TeacherRepository teacherRepository = new TeacherDbRepository();
                Long id = r.getLong("id");
                Lesson lesson = lessonsMap.get(id);
                if(lesson == null) {
                    lesson = Lesson.builder()
                            .id(id)
                            .lessonNumber(r.getInt("lesson_serial_number"))
                            .grade(grade)
                            .weekday(r.getInt("weekday"))
                            .room(r.getInt("room"))
                            .subject(subjectRepository.find(r.getLong("subject_id")))
                            .teacher(teacherRepository.find(r.getLong("teacher_id")))
                            .startTime(r.getString("start_time"))
                            .endTime(r.getString("end_time"))
                            .build();
                    lessons.add(lesson);
                    lessonsMap.put(id, lesson);
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            try{
                DbUtils.close(r);
                DbUtils.close(st);
                DbUtils.close(connection);
            } catch (SQLException ignored) {}
        }
        return lessons;
    }

    @Override
    public List<Lesson> findByWeekdayAndGradeAndSubjectAndTeacher(Integer weekday, Grade grade, Subject subject, Teacher teacher) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Lesson> lessons = new ArrayList<>();
        Map<Long, Lesson> lessonsMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_WEEKDAY_AND_GRADE_AND_SUBJECT_AND_TEACHER);
            st.setInt(1, weekday);
            st.setLong(2, grade.getId());
            st.setLong(3, subject.getId());
            st.setLong(4, teacher.getId());
            r = st.executeQuery();
            while(r.next()){
                Long id = r.getLong("id");
                Lesson lesson = lessonsMap.get(id);
                if(lesson == null) {
                    lesson = Lesson.builder()
                            .id(id)
                            .lessonNumber(r.getInt("lesson_serial_number"))
                            .grade(grade)
                            .weekday(weekday)
                            .room(r.getInt("room"))
                            .subject(subject)
                            .teacher(teacher)
                            .startTime(r.getString("start_time"))
                            .endTime(r.getString("end_time"))
                            .build();
                    lessons.add(lesson);
                    lessonsMap.put(id, lesson);
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            try{
                DbUtils.close(r);
                DbUtils.close(st);
                DbUtils.close(connection);
            } catch (SQLException ignored) {}
        }
        return lessons;
    }

    @Override
    public Lesson find(Long id) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Lesson> lessons = new ArrayList<>();
        Map<Long, Lesson> lessonsMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_ID);
            st.setLong(1, id);
            r = st.executeQuery();
            while(r.next()){
                GradeRepository gradeRepository = new GradeDbRepository();
                SubjectRepository subjectRepository = new SubjectDbRepository();
                TeacherRepository teacherRepository = new TeacherDbRepository();
                Lesson lesson = lessonsMap.get(id);
                if(lesson == null) {
                    lesson = Lesson.builder()
                            .id(id)
                            .lessonNumber(r.getInt("lesson_serial_number"))
                            .grade(gradeRepository.find(r.getLong("grade_id")))
                            .weekday(r.getInt("weekday"))
                            .room(r.getInt("room"))
                            .subject(subjectRepository.find(r.getLong("subject_id")))
                            .teacher(teacherRepository.find(r.getLong("teacher_id")))
                            .startTime(r.getString("start_time"))
                            .endTime(r.getString("end_time"))
                            .build();
                    lessons.add(lesson);
                    lessonsMap.put(id, lesson);
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            try{
                DbUtils.close(r);
                DbUtils.close(st);
                DbUtils.close(connection);
            } catch (SQLException ignored) {}
        }
        if(lessons.size() > 0) {
            return lessons.get(0);
        }
        return null;
    }

    @Override
    public void save(Lesson model) {
        //не юзается
    }

    @Override
    public List<Lesson> findAll() {
        //не юзается
        return null;
    }
    @Override
    public void update(Lesson model) {
        //не юзается
    }
    @Override
    public void delete(Long id) {
        //не юзается
    }
}
