package ru.kpfu.database.repositories.student;

import ru.kpfu.database.repositories.CrudRepository;
import ru.kpfu.entities.Grade;
import ru.kpfu.entities.Student;
import ru.kpfu.entities.Teacher;
import ru.kpfu.exceptions.database.DbException;

import java.util.List;

/**
 * Created by Ильшат on 05.11.2017.
 */
public interface StudentRepository extends CrudRepository<Student> {
    Student findByLogin(String encryptedLogin) throws DbException;
    List<Student> findByGrade(Grade grade) throws DbException;
}
