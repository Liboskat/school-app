package ru.kpfu.database.repositories.homework;

import org.apache.commons.dbutils.DbUtils;
import ru.kpfu.database.repositories.lesson.LessonDbRepository;
import ru.kpfu.database.repositories.lesson.LessonRepository;
import ru.kpfu.database.utils.DBWrapper;
import ru.kpfu.entities.*;
import ru.kpfu.exceptions.database.DbException;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by Ильшат on 08.11.2017.
 */
public class HomeworkDbRepository implements HomeworkRepository {
    private Connection connection;

    public static final String SELECT_BY_GRADE = "SELECT homework.id AS homework_id, lesson_id, task, date " +
            "FROM homework " +
            "INNER JOIN timetable ON lesson_id = timetable.id " +
            "WHERE grade_id = ?";
    public static final String SELECT_BY_GRADE_AND_TEACHER_AND_SUBJECT = "SELECT homework.id AS homework_id, lesson_id, task, date " +
            "FROM homework " +
            "INNER JOIN timetable ON lesson_id = timetable.id " +
            "WHERE grade_id = ? AND subject_id = ? AND teacher_id = ?";
    public static final String INSERT = "INSERT INTO homework (lesson_id, task, date) VALUES (?, ?, ?)";
    public static final String UPDATE = "UPDATE homework SET lesson_id = ?, task = ?, date = ? WHERE id = ?";
    public static final String SELECT_BY_GRADE_AND_WEEK = "SELECT homework.id AS homework_id, lesson_id, task, date " +
            "FROM homework " +
            "INNER JOIN timetable ON lesson_id = timetable.id " +
            "WHERE grade_id = ? AND EXTRACT(WEEK FROM date) = ?";
    public static final String SELECT_BY_GRADE_AND_TEACHER_AND_SUBJECT_AND_DATE = "SELECT homework.id AS homework_id, lesson_id, task " +
            "FROM homework " +
            "INNER JOIN timetable ON lesson_id = timetable.id " +
            "WHERE grade_id = ? AND subject_id = ? AND teacher_id = ? AND date = ?";
    public static final String SELECT_BY_ID = "SELECT lesson_id, task, date " +
            "FROM homework " +
            "WHERE id = ?";
    public static final String DELETE_BY_ID = "DELETE FROM homework WHERE id = ?";

    @Override
    public Homework findByGradeAndTeacherAndSubjectAndDate(Grade grade, Teacher teacher, Subject subject, Date date) throws DbException {
        connection = DBWrapper.getConnection();
        List<Homework> tasks = new ArrayList<>();
        Map<Long, Homework> hwMap = new HashMap<>();

        PreparedStatement st = null;
        ResultSet r = null;
        try {
            st = connection.prepareStatement(SELECT_BY_GRADE_AND_TEACHER_AND_SUBJECT_AND_DATE);
            st.setLong(1, grade.getId());
            st.setLong(2, subject.getId());
            st.setLong(3, teacher.getId());
            st.setDate(4, new java.sql.Date(date.getTime()));
            r = st.executeQuery();
            while(r.next()){
                LessonRepository lessonRepository = new LessonDbRepository();
                Long id = r.getLong("homework_id");
                Homework homework = hwMap.get(id);
                if(homework == null) {
                    homework = Homework.builder()
                            .id(id)
                            .task(r.getString("task"))
                            .date(date)
                            .lesson(lessonRepository.find(r.getLong("lesson_id")))
                            .build();
                    tasks.add(homework);
                    hwMap.put(id, homework);
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
        if(tasks.size() > 0) {
            return tasks.get(0);
        }
        return null;
    }

    @Override
    public List<Homework> findByGrade(Grade grade) throws DbException {
        connection = DBWrapper.getConnection();
        List<Homework> tasks = new ArrayList<>();
        Map<Long, Homework> hwMap = new HashMap<>();

        PreparedStatement st = null;
        ResultSet r = null;

        try {
            st = connection.prepareStatement(SELECT_BY_GRADE);
            st.setLong(1, grade.getId());
            r = st.executeQuery();
            while(r.next()){
                LessonRepository lessonRepository = new LessonDbRepository();
                Long id = r.getLong("homework_id");
                Homework homework = hwMap.get(id);
                if(homework == null) {
                    homework = Homework.builder()
                            .id(id)
                            .task(r.getString("task"))
                            .lesson(lessonRepository.find(r.getLong("lesson_id")))
                            .date(new Date(r.getDate("date").getTime()))
                            .build();
                    tasks.add(homework);
                    hwMap.put(id, homework);
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
        return tasks;
    }

    @Override
    public List<Homework> findByGradeAndTeacherAndSubject(Grade grade, Teacher teacher, Subject subject) throws DbException {
        connection = DBWrapper.getConnection();
        List<Homework> tasks = new ArrayList<>();
        Map<Long, Homework> hwMap = new HashMap<>();

        PreparedStatement st = null;
        ResultSet r = null;
        try {
            st = connection.prepareStatement(SELECT_BY_GRADE_AND_TEACHER_AND_SUBJECT);
            st.setLong(1, grade.getId());
            st.setLong(2, subject.getId());
            st.setLong(3, teacher.getId());
            r = st.executeQuery();
            while(r.next()){
                LessonRepository lessonRepository = new LessonDbRepository();
                Long id = r.getLong("homework_id");
                Homework homework = hwMap.get(id);
                if(homework == null) {
                    homework = Homework.builder()
                            .id(id)
                            .task(r.getString("task"))
                            .date(new Date(r.getDate("date").getTime()))
                            .lesson(lessonRepository.find(r.getLong("lesson_id")))
                            .build();
                    tasks.add(homework);
                    hwMap.put(id, homework);
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
        return tasks;
    }

    @Override
    public List<Homework> findByGradeAndWeek(Grade grade, Integer week) throws DbException {
        connection = DBWrapper.getConnection();
        List<Homework> tasks = new ArrayList<>();
        Map<Long, Homework> hwMap = new HashMap<>();

        PreparedStatement st = null;
        ResultSet r = null;

        try {
            st = connection.prepareStatement(SELECT_BY_GRADE_AND_WEEK);
            st.setLong(1, grade.getId());
            st.setInt(2, week);
            r = st.executeQuery();
            while(r.next()){
                LessonRepository lessonRepository = new LessonDbRepository();
                Long id = r.getLong("homework_id");
                Homework homework = hwMap.get(id);
                if(homework == null) {
                    homework = Homework.builder()
                            .id(id)
                            .task(r.getString("task"))
                            .lesson(lessonRepository.find(r.getLong("lesson_id")))
                            .date(new Date(r.getDate("date").getTime()))
                            .build();
                    tasks.add(homework);
                    hwMap.put(id, homework);
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
        return tasks;
    }

    @Override
    public void save(Homework model) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement(INSERT);
            st.setLong(1, model.getLesson().getId());
            st.setString(2, model.getTask());
            st.setDate(3, new java.sql.Date(model.getDate().getTime()));
            st.execute();
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            try{
                DbUtils.close(st);
                DbUtils.close(connection);
            } catch (SQLException ignored) {}
        }
    }

    @Override
    public void update(Homework model) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement(UPDATE);
            st.setLong(1, model.getLesson().getId());
            st.setString(2, model.getTask());
            st.setDate(3, new java.sql.Date(model.getDate().getTime()));
            st.setLong(4, model.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            try{
                DbUtils.close(st);
                DbUtils.close(connection);
            } catch (SQLException ignored) {}
        }
    }

    @Override
    public Homework find(Long id) throws DbException {
        connection = DBWrapper.getConnection();
        List<Homework> tasks = new ArrayList<>();
        Map<Long, Homework> hwMap = new HashMap<>();

        PreparedStatement st = null;
        ResultSet r = null;

        try {
            st = connection.prepareStatement(SELECT_BY_ID);
            st.setLong(1, id);
            r = st.executeQuery();
            while(r.next()){
                LessonRepository lessonRepository = new LessonDbRepository();
                Homework homework = hwMap.get(id);
                if(homework == null) {
                    homework = Homework.builder()
                            .id(id)
                            .task(r.getString("task"))
                            .lesson(lessonRepository.find(r.getLong("lesson_id")))
                            .date(new Date(r.getDate("date").getTime()))
                            .build();
                    tasks.add(homework);
                    hwMap.put(id, homework);
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
        if(tasks.size() > 0) {
            return tasks.get(0);
        }
        return null;
    }

    @Override
    public void delete(Long id) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(DELETE_BY_ID);
            st.setLong(1, id);
            st.execute();
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            try{
                DbUtils.close(st);
                DbUtils.close(connection);
            } catch (SQLException ignored) {}
        }
    }

    @Override
    public List<Homework> findAll() {
        //не юзается
        return null;
    }
}
