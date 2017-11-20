package ru.kpfu.database.repositories.student;

import org.apache.commons.dbutils.DbUtils;
import ru.kpfu.database.repositories.grade.GradeDbRepository;
import ru.kpfu.database.repositories.grade.GradeRepository;
import ru.kpfu.database.utils.DBWrapper;
import ru.kpfu.entities.Grade;
import ru.kpfu.entities.Student;
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
 * Created by Ильшат on 05.11.2017.
 */
public class StudentDbRepository implements StudentRepository {
    private Connection connection;

    public static final String SELECT_BY_LOGIN = "SELECT student.id AS student_id, student.invite AS student_invite, grade_id, name, surname " +
            "FROM student " +
            "INNER JOIN users ON student.invite = users.invite " +
            "WHERE login = ?";
    public static final String SELECT_BY_GRADE = "SELECT id, invite, name, surname " +
            "FROM student " +
            "WHERE grade_id = ?";
    public static final String SELECT_BY_ID = "SELECT invite, grade_id, name, surname " +
            "FROM student " +
            "WHERE id = ?";
    public static final String INSERT = "INSERT INTO student (invite, grade_id, name, surname) VALUES (?, ?, ?, ?)";

    @Override
    public Student findByLogin(String login) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Student> students = new ArrayList<>();
        Map<Long, Student> studentMap = new HashMap<>();
        GradeRepository gradeRepository = new GradeDbRepository();
        try {
            st = connection.prepareStatement(SELECT_BY_LOGIN);
            st.setString(1, login);
            r = st.executeQuery();
            while(r.next()){
                Long id = r.getLong("student_id");
                Student student = studentMap.get(id);
                if(student == null) {
                    student = Student.builder()
                            .id(id)
                            .invite(r.getString("student_invite"))
                            .grade(gradeRepository.find(r.getLong("grade_id")))
                            .name(r.getString("name"))
                            .surname(r.getString("surname"))
                            .build();
                    students.add(student);
                    studentMap.put(id, student);
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
        if(students.size() > 0) {
            return students.get(0);
        }
        return null;
    }

    @Override
    public List<Student> findByGrade(Grade grade) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Student> students = new ArrayList<>();
        Map<Long, Student> studentMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_GRADE);
            st.setLong(1, grade.getId());
            r = st.executeQuery();
            while(r.next()){
                Long id = r.getLong("id");
                Student student = studentMap.get(id);
                if(student == null) {
                    student = Student.builder()
                            .id(id)
                            .invite(r.getString("invite"))
                            .grade(grade)
                            .name(r.getString("name"))
                            .surname(r.getString("surname"))
                            .build();
                    students.add(student);
                    studentMap.put(id, student);
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
        return students;
    }

    @Override
    public Student find(Long id) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Student> students = new ArrayList<>();
        Map<Long, Student> studentMap = new HashMap<>();
        GradeRepository gradeRepository = new GradeDbRepository();
        try {
            st = connection.prepareStatement(SELECT_BY_ID);
            st.setLong(1, id);
            r = st.executeQuery();
            while(r.next()){
                Student student = studentMap.get(id);
                if(student == null) {
                    student = Student.builder()
                            .id(id)
                            .invite(r.getString("invite"))
                            .grade(gradeRepository.find(r.getLong("grade_id")))
                            .name(r.getString("name"))
                            .surname(r.getString("surname"))
                            .build();
                    students.add(student);
                    studentMap.put(id, student);
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
        if(students.size() > 0) {
            return students.get(0);
        }
        return null;
    }

    @Override
    public void save(Student model) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement(INSERT);
            st.setString(1, model.getInvite());
            st.setLong(2, model.getGrade().getId());
            st.setString(3, model.getName());
            st.setString(4, model.getSurname());
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
    public void update(Student model) {
        //не юзается
    }
    @Override
    public void delete(Long id) {
        //не юзается
    }
    @Override
    public List<Student> findAll() {
        //не юзается
        return null;
    }
}
