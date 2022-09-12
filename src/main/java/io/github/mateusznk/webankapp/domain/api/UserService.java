package io.github.mateusznk.webankapp.domain.api;

import io.github.mateusznk.webankapp.domain.account.AccountDao;
import io.github.mateusznk.webankapp.domain.user.User;
import io.github.mateusznk.webankapp.domain.user.UserDao;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.OptionalInt;

public class UserService {
    private UserDao userDao = new UserDao();
    private AccountDao accountDao = new AccountDao();

    public void register(UserRegistration userRegistration) {
        User userToSave = UserMapper.map(userRegistration);
        try {
            hashPasswordWithSha256(userToSave);
            userDao.save(userToSave);
            OptionalInt id = userDao.findUser(userToSave.getUsername());
            if (id.isEmpty()) {
                throw new UnknownError();
            }
            accountDao.createNewAccount(id.getAsInt());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void hashPasswordWithSha256(User user) throws NoSuchAlgorithmException {
        String sha256Password = DigestUtils.sha256Hex(user.getPassword());
        user.setPassword(sha256Password);
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


    public OptionalInt findIdOfAccount(String username) {
        return userDao.findUser(username);
    }
}
