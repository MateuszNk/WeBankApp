package io.github.mateusznk.webankapp.domain.api;

import io.github.mateusznk.webankapp.domain.user.User;
import io.github.mateusznk.webankapp.domain.user.UserDao;

import java.time.LocalDateTime;

public class UserService {
    private UserDao userDao = new UserDao();

    public void register(UserRegistration userRegistration) {
        User userToSave = UserMapper.map(userRegistration);
        userDao.save(userToSave);
    }

    private static class UserMapper {
        static User map(UserRegistration userRegistration) {
            return new User(
                    userRegistration.getUsername(),
                    userRegistration.getPassword(),
                    userRegistration.getEmail(),
                    userRegistration.getPhoneNumber(),
                    LocalDateTime.now(),
                    userRegistration.getNewsletter()
            );
        }
    }
}
