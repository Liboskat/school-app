package ru.kpfu.database.repositories.grade;

import org.apache.commons.dbutils.DbUtils;
import ru.kpfu.database.utils.DBWrapper;
import ru.kpfu.entities.Grade;
import ru.kpfu.entities.Student;
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
 * Created by Ильшат on 05.11.2017.
 */
public class GradeDbRepository implements GradeRepository {
    private Connection connection;

    public final static String SELECT_BY_ID = "SELECT start_year, letter, " +
            "student.id AS student_id, invite, name, surname " +
            "FROM grade " +
            "LEFT JOIN student ON student.grade_id = grade.id " +
            "WHERE grade.id = ?";
    public final static String SELECT_BY_TEACHER = "SELECT grade.id AS grade_id, start_year, letter, " +
            "student.id AS student_id, invite, name, surname " +
            "FROM grade " +
            "INNER JOIN timetable ON grade.id = timetable.grade_id " +
            "LEFT JOIN student ON student.grade_id = grade.id " +
            "WHERE teacher_id = ?";
    public final static String SELECT_ALL = "SELECT grade.id AS grade_id, start_year, letter, " +
            "student.id AS student_id, invite, name, surname " +
            "FROM grade " +
            "LEFT JOIN student ON student.grade_id = grade.id";

    @Override
    public Grade find(Long id) throws DbException {
        connection = DBWrapper.getConnection();
        Map<Long, Grade> gradeMap = new HashMap<>();
        List<Grade> grades = new ArrayList<>();

        PreparedStatement st = null;
        ResultSet r = null;
        try {
            st = connection.prepareStatement(SELECT_BY_ID);
            st.setLong(1, id);
            r = st.executeQuery();
            while(r.next()){
                Grade grade = gradeMap.get(id);
                if(grade == null) {
                    grade = Grade.builder()
                            .id(id)
                            .letter(r.getInt("letter"))
                            .start_year(r.getInt("start_year"))
                            .students(new ArrayList<Student>())
                            .build();
                    gradeMap.put(id, grade);
                    grades.add(grade);
                }
                Student addedStudent = Student.builder()
                        .name(r.getString("name"))
                        .id(r.getLong("student_id"))
                        .surname(r.getString("surname"))
                        .invite(r.getString("invite"))
                        .build();
                grade.addStudent(addedStudent);
                addedStudent.setGrade(grade);
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
        if(grades.size() > 0) {
            return grades.get(0);
        }
        return null;
    }

    @Override
    public List<Grade> findAll() throws DbException {
        connection = DBWrapper.getConnection();
        Map<Long, Grade> gradeMap = new HashMap<>();
        List<Grade> grades = new ArrayList<>();

        PreparedStatement st = null;
        ResultSet r = null;
        try {
            st = connection.prepareStatement(SELECT_ALL);
            r = st.executeQuery();
            while(r.next()){
                Long gradeId = r.getLong("grade_id");
                Grade grade = gradeMap.get(gradeId);
                if(grade == null) {
                    grade = Grade.builder()
                            .id(gradeId)
                            .letter(r.getInt("letter"))
                            .start_year(r.getInt("start_year"))
                            .students(new ArrayList<Student>())
                            .build();
                    gradeMap.put(gradeId, grade);
                    grades.add(grade);
                }
                Student addedStudent = Student.builder()
                        .name(r.getString("name"))
                        .id(r.getLong("student_id"))
                        .surname(r.getString("surname"))
                        .invite(r.getString("invite"))
                        .build();
                grade.addStudent(addedStudent);
                addedStudent.setGrade(grade);
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
        return grades;
    }

    @Override
    public List<Grade> findByTeacher(Teacher teacher) throws DbException {
        connection = DBWrapper.getConnection();
        Map<Long, Grade> gradeMap = new HashMap<>();
        Map<Long, Student> studentForGradeMap = new HashMap<>();
        List<Grade> grades = new ArrayList<>();

        PreparedStatement st = null;
        ResultSet r = null;
        try {
            st = connection.prepareStatement(SELECT_BY_TEACHER);
            st.setLong(1, teacher.getId());
            r = st.executeQuery();
            while(r.next()){
                Long gradeId = r.getLong("grade_id");
                Grade grade = gradeMap.get(gradeId);
                if(grade == null) {
                    grade = Grade.builder()
                            .id(gradeId)
                            .letter(r.getInt("letter"))
                            .start_year(r.getInt("start_year"))
                            .students(new ArrayList<Student>())
                            .build();
                    gradeMap.put(gradeId, grade);
                    grades.add(grade);
                    studentForGradeMap = new HashMap<>();
                }
                Long studentId = r.getLong("student_id");
                Student addedStudent = studentForGradeMap.get(studentId);
                if(addedStudent == null) {
                    addedStudent = Student.builder()
                            .name(r.getString("name"))
                            .id(studentId)
                            .surname(r.getString("surname"))
                            .invite(r.getString("invite"))
                            .build();
                    studentForGradeMap.put(studentId, addedStudent);
                    grade.addStudent(addedStudent);
                    addedStudent.setGrade(grade);
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
        return grades;
    }

    @Override
    public void update(Grade model) throws DbException {
        //не юзается
    }

    @Override
    public void delete(Long id) {
        //не юзается
    }

    @Override
    public void save(Grade model) {
        //не юзается
    }
}
