package ru.kpfu.database.repositories.teacher;

import ru.kpfu.database.repositories.CrudRepository;
import ru.kpfu.entities.Teacher;
import ru.kpfu.exceptions.database.DbException;

/**
 * Created by Ильшат on 08.11.2017.
 */
public interface TeacherRepository extends CrudRepository<Teacher> {
    Teacher findByLogin(String encryptedLogin) throws DbException;
}
