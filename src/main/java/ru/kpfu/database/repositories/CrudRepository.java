package ru.kpfu.database.repositories;

import ru.kpfu.exceptions.database.DbException;

import java.util.List;

/**
 * Created by Ильшат on 01.11.2017.
 */
public interface CrudRepository<T> {
    T find(Long id) throws DbException;
    List<T> findAll() throws DbException;
    void save(T model) throws DbException;
    void update(T model) throws DbException;
    void delete(Long id) throws DbException;
}
