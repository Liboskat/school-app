package ru.kpfu.database.utils;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.kpfu.exceptions.database.DbException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Ильшат on 21.10.2017.
 */
public class DBWrapper {
    public static Connection getConnection() throws DbException {
        try {
            Class.forName("org.postgresql.Driver");
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl("jdbc:postgresql://localhost:5432/school_db");
            dataSource.setUsername("postgres");
            dataSource.setPassword("ugandatunis");
            return dataSource.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}