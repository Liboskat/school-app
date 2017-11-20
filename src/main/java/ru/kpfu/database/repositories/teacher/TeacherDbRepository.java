package ru.kpfu.database.repositories.teacher;

import org.apache.commons.dbutils.DbUtils;
import ru.kpfu.database.utils.DBWrapper;
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
public class TeacherDbRepository implements TeacherRepository{
    private Connection connection;

    public static final String SELECT_BY_LOGIN = "SELECT teacher.id AS teacher_id, teacher.invite AS teacher_invite, name FROM teacher " +
            "INNER JOIN users ON teacher.invite = users.invite WHERE login = ?";
    public static final String SELECT_BY_ID = "SELECT teacher.invite AS teacher_invite, name FROM teacher " +
            "WHERE id = ?";
    public static final String INSERT = "INSERT INTO teacher (invite, name) VALUES (?, ?)";

    @Override
    public Teacher findByLogin(String login) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Teacher> teachers = new ArrayList<>();
        Map<Long, Teacher> teacherMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_LOGIN);
            st.setString(1, login);
            r = st.executeQuery();
            while(r.next()){
                Long id = r.getLong("teacher_id");
                Teacher teacher = teacherMap.get(id);
                if(teacher == null) {
                    teacher = Teacher.builder()
                            .id(id)
                            .invite(r.getString("teacher_invite"))
                            .name(r.getString("name"))
                            .build();
                    teachers.add(teacher);
                    teacherMap.put(id, teacher);
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
        if(teachers.size() > 0) {
            return teachers.get(0);
        }
        return null;
    }

    @Override
    public void save(Teacher model) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement(INSERT);
            st.setString(1, model.getInvite());
            st.setString(2, model.getName());
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
    public Teacher find(Long id) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<Teacher> teachers = new ArrayList<>();
        Map<Long, Teacher> teacherMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_ID);
            st.setLong(1, id);
            r = st.executeQuery();
            while(r.next()){
                Teacher teacher = teacherMap.get(id);
                if(teacher == null) {
                    teacher = Teacher.builder()
                            .id(id)
                            .invite(r.getString("teacher_invite"))
                            .name(r.getString("name"))
                            .build();
                    teachers.add(teacher);
                    teacherMap.put(id, teacher);
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
        if(teachers.size() > 0) {
            return teachers.get(0);
        }
        return null;
    }
    @Override
    public void update(Teacher model) throws DbException {
        //не юзается
    }
    @Override
    public void delete(Long id) {
        //не юзается
    }
    @Override
    public List<Teacher> findAll() {
        //не юзается
        return null;
    }

}
