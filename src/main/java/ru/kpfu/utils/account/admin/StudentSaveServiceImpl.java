package ru.kpfu.utils.account.admin;

import ru.kpfu.database.repositories.student.StudentDbRepository;
import ru.kpfu.database.repositories.student.StudentRepository;
import ru.kpfu.database.repositories.user.UserDbRepository;
import ru.kpfu.database.repositories.user.UserRepository;
import ru.kpfu.entities.Grade;
import ru.kpfu.entities.Student;
import ru.kpfu.entities.User;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.utils.account.UserRoleGetter;

/**
 * Created by Ильшат on 08.11.2017.
 */
public class StudentSaveServiceImpl implements StudentSaveService{
    public void save(Grade grade, String name, String surname, String invite) throws DbException {
        Student student = Student.builder()
                .grade(grade)
                .invite(invite)
                .name(name)
                .surname(surname)
                .build();
        User user = User.builder()
                .invite(invite)
                .role(UserRoleGetter.STUDENT_ROLE)
                .build();

        UserRepository userRepository = new UserDbRepository();
        userRepository.save(user);

        StudentRepository studentRepository = new StudentDbRepository();
        studentRepository.save(student);
    }
}
