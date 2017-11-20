package ru.kpfu.database.repositories.mark;

import org.apache.commons.dbutils.DbUtils;
import ru.kpfu.database.repositories.lesson.LessonDbRepository;
import ru.kpfu.database.repositories.lesson.LessonRepository;
import ru.kpfu.database.repositories.student.StudentDbRepository;
import ru.kpfu.database.repositories.student.StudentRepository;
import ru.kpfu.database.utils.DBWrapper;
import ru.kpfu.entities.*;
import ru.kpfu.exceptions.database.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Ильшат on 08.11.2017.
 */
public class MarkDbRepository implements MarkRepository {
    private Connection connection;

    public static final String SELECT_BY_STUDENT = "SELECT id, mark_value, lesson_id, date " +
            "FROM mark " +
            "WHERE student_id = ?";
    public static final String SELECT_BY_GRADE_AND_TEACHER_AND_SUBJECT = "SELECT mark.id AS mark_id, mark_value, student_id, lesson_id, date " +
            "FROM mark " +
            "INNER JOIN timetable ON lesson_id = timetable.id " +
            "WHERE grade_id = ? AND teacher_id = ? AND subject_id = ?";
    public static final String INSERT = "INSERT INTO mark (student_id, mark_value, lesson_id, date) VALUES (?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE mark " +
            "SET student_id = ?, mark_value = ?, lesson_id = ?, date = ? WHERE id = ?";
    public static final String SELECT_BY_STUDENT_AND_MONTH = "SELECT id, mark_value, lesson_id, date " +
            "FROM mark " +
            "WHERE student_id = ? AND EXTRACT(MONTH FROM date) = ?";
    public static final String SELECT_BY_GRADE_AND_TEACHER_AND_SUBJECT_AND_MONTH = "SELECT mark.id AS mark_id, mark_value, " +
            "student_id, lesson_id, date " +
            "FROM mark " +
            "INNER JOIN timetable ON lesson_id = timetable.id " +
            "WHERE grade_id = ? AND teacher_id = ? AND subject_id = ? AND EXTRACT(MONTH FROM date) = ?";
    public static final String SELECT_BY_STUDENT_AND_SUBJECT_AND_QUARTER = "SELECT mark.id AS mark_id, mark_value, lesson_id, date " +
            "FROM mark " +
            "INNER JOIN timetable ON lesson_id = timetable.id " +
            "INNER JOIN quarter_dates ON quarter_number = ? " +
            "WHERE student_id = ? AND subject_id = ? AND date >= quarter_dates.start_time AND date <= quarter_dates.end_time";
    public static final String SELECT_BY_STUDENT_AND_SUBJECT = "SELECT mark.id AS mark_id, mark_value, lesson_id, date " +
            "FROM mark " +
            "INNER JOIN timetable ON lesson_id = timetable.id " +
            "WHERE student_id = ? AND subject_id = ?";

    @Override
    public List<Mark> findBySubjectAndTeacherAndGradeAndMonth(Subject subject, Teacher teacher, Grade grade, Integer monthNumber) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Mark> marks = new ArrayList<>();
        Map<Long, Mark> markMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_GRADE_AND_TEACHER_AND_SUBJECT_AND_MONTH);
            st.setLong(1, grade.getId());
            st.setLong(2, teacher.getId());
            st.setLong(3, subject.getId());
            st.setInt(4, monthNumber);
            r = st.executeQuery();
            while(r.next()){
                LessonRepository lessonRepository = new LessonDbRepository();
                StudentRepository studentRepository = new StudentDbRepository();
                Long id = r.getLong("mark_id");
                Mark mark = markMap.get(id);
                if(mark == null) {
                    mark = Mark.builder()
                            .id(id)
                            .student(studentRepository.find(r.getLong("student_id")))
                            .date(new Date(r.getDate("date").getTime()))
                            .lesson(lessonRepository.find(r.getLong("lesson_id")))
                            .value(r.getInt("mark_value"))
                            .build();
                    marks.add(mark);
                    markMap.put(id, mark);
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
        return marks;
    }

    @Override
    public List<Mark> findByStudentAndSubjectAndQuarter(Student student, Subject subject, Integer quarter) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Mark> marks = new ArrayList<>();
        Map<Long, Mark> markMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_STUDENT_AND_SUBJECT_AND_QUARTER);
            st.setInt(1, quarter);
            st.setLong(2, student.getId());
            st.setLong(3, subject.getId());
            r = st.executeQuery();
            while(r.next()){
                LessonRepository lessonRepository = new LessonDbRepository();
                Long id = r.getLong("mark_id");
                Mark mark = markMap.get(id);
                if(mark == null) {
                    mark = Mark.builder()
                            .id(id)
                            .student(student)
                            .date(new Date(r.getDate("date").getTime()))
                            .lesson(lessonRepository.find(r.getLong("lesson_id")))
                            .value(r.getInt("mark_value"))
                            .build();
                    marks.add(mark);
                    markMap.put(id, mark);
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
        return marks;
    }

    @Override
    public List<Mark> findByStudentAndSubject(Student student, Subject subject) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Mark> marks = new ArrayList<>();
        Map<Long, Mark> markMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_STUDENT_AND_SUBJECT);
            st.setLong(1, student.getId());
            st.setLong(2, subject.getId());
            r = st.executeQuery();
            while(r.next()){
                LessonRepository lessonRepository = new LessonDbRepository();
                Long id = r.getLong("mark_id");
                Mark mark = markMap.get(id);
                if(mark == null) {
                    mark = Mark.builder()
                            .id(id)
                            .student(student)
                            .date(new Date(r.getDate("date").getTime()))
                            .lesson(lessonRepository.find(r.getLong("lesson_id")))
                            .value(r.getInt("mark_value"))
                            .build();
                    marks.add(mark);
                    markMap.put(id, mark);
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
        return marks;
    }

    @Override
    public List<Mark> findByStudent(Student student) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Mark> marks = new ArrayList<>();
        Map<Long, Mark> markMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_STUDENT);
            st.setLong(1, student.getId());
            r = st.executeQuery();
            while(r.next()){
                LessonRepository lessonRepository = new LessonDbRepository();
                Long id = r.getLong("id");
                Mark mark = markMap.get(id);
                if(mark == null) {
                    mark = Mark.builder()
                            .id(id)
                            .student(student)
                            .date(new Date(r.getDate("date").getTime()))
                            .lesson(lessonRepository.find(r.getLong("lesson_id")))
                            .value(r.getInt("mark_value"))
                            .build();
                    marks.add(mark);
                    markMap.put(id, mark);
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
        return marks;
    }


    @Override
    public List<Mark> findByGradeAndTeacherAndSubject(Grade grade, Teacher teacher, Subject subject) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Mark> marks = new ArrayList<>();
        Map<Long, Mark> markMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_GRADE_AND_TEACHER_AND_SUBJECT);
            st.setLong(1, grade.getId());
            st.setLong(2, teacher.getId());
            st.setLong(3, subject.getId());
            r = st.executeQuery();
            while(r.next()){
                LessonRepository lessonRepository = new LessonDbRepository();
                StudentRepository studentRepository = new StudentDbRepository();
                Long id = r.getLong("mark_id");
                Mark mark = markMap.get(id);
                if(mark == null) {
                    mark = Mark.builder()
                            .id(id)
                            .student(studentRepository.find(r.getLong("student_id")))
                            .date(new Date(r.getDate("date").getTime()))
                            .lesson(lessonRepository.find(r.getLong("lesson_id")))
                            .value(r.getInt("mark_value"))
                            .build();
                    marks.add(mark);
                    markMap.put(id, mark);
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
        return marks;
    }

    @Override
    public List<Mark> findByStudentAndMonth(Student student, Integer monthNumber) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Mark> marks = new ArrayList<>();
        Map<Long, Mark> markMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_STUDENT_AND_MONTH);
            st.setLong(1, student.getId());
            st.setInt(2, monthNumber);
            r = st.executeQuery();
            while(r.next()){
                LessonRepository lessonRepository = new LessonDbRepository();
                Long id = r.getLong("id");
                Mark mark = markMap.get(id);
                if(mark == null) {
                    mark = Mark.builder()
                            .id(id)
                            .student(student)
                            .date(new Date(r.getDate("date").getTime()))
                            .lesson(lessonRepository.find(r.getLong("lesson_id")))
                            .value(r.getInt("mark_value"))
                            .build();
                    marks.add(mark);
                    markMap.put(id, mark);
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
        return marks;
    }

    @Override
    public void save(Mark model) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement(INSERT);
            st.setLong(1, model.getStudent().getId());
            st.setInt(2, model.getValue());
            st.setLong(3, model.getLesson().getId());
            st.setDate(4, new java.sql.Date(model.getDate().getTime()));
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
    public void update(Mark model) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement(UPDATE);
            st.setLong(1, model.getStudent().getId());
            st.setInt(2, model.getValue());
            st.setLong(3, model.getLesson().getId());
            st.setDate(4, new java.sql.Date(model.getDate().getTime()));
            st.setLong(5, model.getId());
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
    public Mark find(Long id) {
        //не юзается
        return null;
    }
    @Override
    public List<Mark> findAll() {
        //не юзается
        return null;
    }
    @Override
    public void delete(Long id) {
        //не юзается
    }
}
