package ru.kpfu.database.repositories.user;

import org.apache.commons.dbutils.DbUtils;
import ru.kpfu.database.utils.DBWrapper;
import ru.kpfu.entities.User;
import ru.kpfu.exceptions.database.DbException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ильшат on 01.11.2017.
 */
public class UserDbRepository implements UserRepository{
    private Connection connection;

    public static final String SELECT_BY_INVITE_CODE = "SELECT id, login, password, type FROM users " +
            "WHERE invite = ?";
    public static final String SELECT_BY_LOGIN = "SELECT id, invite, password, type FROM users " +
            "WHERE login = ?";
    public static final String INSERT = "INSERT INTO users (invite, login, password, type) VALUES (?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE users SET invite = ?, login = ?, password = ?, type = ? " +
            "WHERE id = ?";

    @Override
    public User findByInviteCode(String inviteCode) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<User> users = new ArrayList<>();
        Map<Long, User> userMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_INVITE_CODE);
            st.setString(1, inviteCode);
            r = st.executeQuery();
            while(r.next()){
                Long id = r.getLong("id");
                User user = userMap.get(id);
                if(user == null) {
                    user = User.builder()
                            .id(id)
                            .invite(inviteCode)
                            .login(r.getString("login"))
                            .role(r.getString("type"))
                            .password(r.getString("password"))
                            .build();
                    users.add(user);
                    userMap.put(id, user);
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
        if(users.size() > 0) {
            return users.get(0);
        }
        return null;
    }
    @Override
    public User findByLogin(String login) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<User> users = new ArrayList<>();
        Map<Long, User> userMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_LOGIN);
            st.setString(1, login);
            r = st.executeQuery();
            while(r.next()){
                Long id = r.getLong("id");
                User user = userMap.get(id);
                if(user == null) {
                    user = User.builder()
                            .id(id)
                            .invite(r.getString("invite"))
                            .login(login)
                            .role(r.getString("type"))
                            .password(r.getString("password"))
                            .build();
                    users.add(user);
                    userMap.put(id, user);
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
        if(users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    public void save(User model) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement(INSERT);
            st.setString(1, model.getInvite());
            st.setString(2, model.getLogin());
            st.setString(3, model.getPassword());
            st.setString(4, model.getRole());
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

    public void update(User model) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement(UPDATE);
            st.setString(1, model.getInvite());
            st.setString(2, model.getLogin());
            st.setString(3, model.getPassword());
            st.setString(4, model.getRole());
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

    public User find(Long id) {
        //не юзается
        return null;
    }
    public List<User> findAll() {
        //не юзается
        return null;
    }
    public void delete(Long id) {
        //не юзается
    }
}
