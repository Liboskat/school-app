package ru.kpfu.database.repositories.usercookiepair;

import org.apache.commons.dbutils.DbUtils;
import ru.kpfu.database.utils.DBWrapper;
import ru.kpfu.entities.Student;
import ru.kpfu.entities.UserCookiePair;
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
 * Created by Ильшат on 01.11.2017.
 */
public class UserCookiePairDbRepository implements UserCookiePairRepository {
    private Connection connection;

    public static final String SELECT_BY_LOGIN = "SELECT number FROM login_randomnumber WHERE login = ?";
    public static final String UPDATE = "UPDATE login_randomnumber SET number = ? WHERE login = ?";
    public static final String INSERT = "INSERT INTO login_randomnumber (login, number) VALUES (?, ?)";

    public void update(UserCookiePair model) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement(UPDATE);
            st.setLong(1, model.getNumber());
            st.setString(2, model.getLogin());
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

    public UserCookiePair findByLogin(String login) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;
        ResultSet r = null;

        List<UserCookiePair> pairs = new ArrayList<>();
        Map<String, UserCookiePair> pairMap = new HashMap<>();
        try {
            st = connection.prepareStatement(SELECT_BY_LOGIN);
            st.setString(1, login);
            r = st.executeQuery();
            while(r.next()){
                UserCookiePair pair = pairMap.get(login);
                if(pair == null) {
                    pair = UserCookiePair.builder()
                            .login(login)
                            .number(r.getLong("number"))
                            .build();
                    pairs.add(pair);
                    pairMap.put(login, pair);
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
        if(pairs.size() > 0) {
            return pairs.get(0);
        }
        return null;
    }

    public void save(UserCookiePair model) throws DbException {
        connection = DBWrapper.getConnection();
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement(INSERT);
            st.setString(1, model.getLogin());
            st.setLong(2, model.getNumber());
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

    public List<UserCookiePair> findAll() {
        //не юзается
        return null;
    }
    public UserCookiePair find(Long id) {
        //не юзается
        return null;
    }
    public void delete(Long id) {
        //не юзается
    }
}
