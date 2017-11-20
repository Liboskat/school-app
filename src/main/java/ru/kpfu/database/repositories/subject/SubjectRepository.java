package ru.kpfu.database.repositories.subject;

import ru.kpfu.database.repositories.CrudRepository;
import ru.kpfu.entities.Grade;
import ru.kpfu.entities.Subject;
import ru.kpfu.entities.Teacher;
import ru.kpfu.exceptions.database.DbException;

import java.util.List;

/**
 * Created by Ильшат on 08.11.2017.
 */
public interface SubjectRepository extends CrudRepository<Subject>{
    public List<Subject> findByTeacherAndGrade(Teacher teacher, Grade grade) throws DbException;
    public List<Subject> findByGrade(Grade grade) throws DbException;
}
