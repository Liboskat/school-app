package ru.kpfu.database.repositories.subject;

import org.apache.commons.dbutils.DbUtils;
import ru.kpfu.database.utils.DBWrapper;
import ru.kpfu.entities.Grade;
import ru.kpfu.entities.Subject;
import ru.kpfu.entities.Teacher;
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
 * Created by Ильшат on 08.11.2017.
 */
public class SubjectDbRepository implements SubjectRepository {
    private Connection connection;

    public static final String SELECT_BY_TEACHER_AND_GRADE = "SELECT subject.id AS subject_id, name FROM subject " +
            "INNER JOIN timetable ON subject.id = timetable.subject_id " +
            "WHERE teacher_id = ? AND grade_id = ?";
    public static final String SELECT_BY_GRADE = "SELECT subject.id AS subject_id, name FROM subject " +
            "INNER JOIN timetable ON subject.id = timetable.subject_id " +
            "WHERE grade_id = ?";
    public static final String SELECT_BY_ID = "SELECT name FROM subject WHERE id = ?";

    @Override
    public List<Subject> findByTeacherAndGrade(Teacher teacher, Grade grade) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Subject> subjects = new ArrayList<>();
        Map<Long, Subject> subjectMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_TEACHER_AND_GRADE);
            st.setLong(1, teacher.getId());
            st.setLong(2, grade.getId());
            r = st.executeQuery();
            while(r.next()){
                Long id = r.getLong("subject_id");
                Subject subject = subjectMap.get(id);
                if(subject == null) {
                    subject = Subject.builder()
                            .id(id)
                            .name(r.getString("name"))
                            .build();
                    subjects.add(subject);
                    subjectMap.put(id, subject);
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
        return subjects;
    }

    @Override
    public List<Subject> findByGrade(Grade grade) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Subject> subjects = new ArrayList<>();
        Map<Long, Subject> subjectMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_GRADE);
            st.setLong(1, grade.getId());
            r = st.executeQuery();
            while(r.next()){
                Long id = r.getLong("subject_id");
                Subject subject = subjectMap.get(id);
                if(subject == null) {
                    subject = Subject.builder()
                            .id(id)
                            .name(r.getString("name"))
                            .build();
                    subjects.add(subject);
                    subjectMap.put(id, subject);
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
        return subjects;
    }

    @Override
    public Subject find(Long id) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Subject> subjects = new ArrayList<>();
        Map<Long, Subject> subjectMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_ID);
            st.setLong(1, id);
            r = st.executeQuery();
            while(r.next()){
                Subject subject = subjectMap.get(id);
                if(subject == null) {
                    subject = Subject.builder()
                            .id(id)
                            .name(r.getString("name"))
                            .build();
                    subjects.add(subject);
                    subjectMap.put(id, subject);
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
        if(subjects.size() > 0) {
            return subjects.get(0);
        }
        return null;
    }

    @Override
    public void save(Subject model) {
        //не юзается
    }
    @Override
    public void update(Subject model) {
        //не юзается
    }
    @Override
    public List<Subject> findAll() {
        //не юзается
        return null;
    }
    @Override
    public void delete(Long id) {
        //не юзается
    }
}
