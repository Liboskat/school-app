package ru.kpfu.utils.account.admin;

import ru.kpfu.database.repositories.teacher.TeacherDbRepository;
import ru.kpfu.database.repositories.teacher.TeacherRepository;
import ru.kpfu.database.repositories.user.UserDbRepository;
import ru.kpfu.database.repositories.user.UserRepository;
import ru.kpfu.entities.Teacher;
import ru.kpfu.entities.User;
import ru.kpfu.exceptions.database.DbException;
import ru.kpfu.utils.account.UserRoleGetter;

/**
 * Created by Ильшат on 12.11.2017.
 */
public class TeacherSaveServiceImpl implements TeacherSaveService{
    public void save(String name, String invite) throws DbException {
        Teacher teacher = Teacher.builder()
                .name(name)
                .invite(invite)
                .build();
        User user = User.builder()
                .invite(invite)
                .role(UserRoleGetter.TEACHER_ROLE)
                .build();

        UserRepository userRepository = new UserDbRepository();
        userRepository.save(user);

        TeacherRepository repository = new TeacherDbRepository();
        repository.save(teacher);
    }
}
