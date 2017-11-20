package ru.kpfu.database.repositories.grade;

import ru.kpfu.database.repositories.CrudRepository;
import ru.kpfu.entities.Grade;
import ru.kpfu.entities.Subject;
import ru.kpfu.entities.Teacher;
import ru.kpfu.exceptions.database.DbException;

import java.util.List;

/**
 * Created by Ильшат on 05.11.2017.
 */
public interface GradeRepository extends CrudRepository<Grade> {
    List<Grade> findByTeacher(Teacher teacher) throws DbException;
}
