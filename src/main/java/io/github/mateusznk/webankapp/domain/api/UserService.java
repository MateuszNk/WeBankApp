package io.github.mateusznk.webankapp.domain.api;

import io.github.mateusznk.webankapp.domain.account.AccountDao;
import io.github.mateusznk.webankapp.domain.user.User;
import io.github.mateusznk.webankapp.domain.user.UserDao;
import io.github.mateusznk.webankapp.logs.WriteExceptionsToFile;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.OptionalInt;

public class UserService {
    private final UserDao userDao = new UserDao();
    private final AccountDao accountDao = new AccountDao();
    private final WriteExceptionsToFile writeExceptionsToFile = new WriteExceptionsToFile();

    public void register(UserRegistration userRegistration) {
        User userToSave = UserMapper.map(userRegistration);
        try {
            hashPasswordWithSha256(userToSave);
            userDao.save(userToSave);
            OptionalInt id = userDao.findUser(userToSave.getUsername());
            if (id.isEmpty()) {
                writeExceptionsToFile.unusualErrorLog(Thread.currentThread().getStackTrace()[1].getLineNumber(),
                        getClass().getName());
                throw new RuntimeException();
            }
            accountDao.createNewAccount(id.getAsInt());
        } catch (NoSuchAlgorithmException e) {
            writeExceptionsToFile.typicalErrorLog(getClass().getName(), e);
            throw new RuntimeException(e);
        }
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

    private void hashPasswordWithSha256(User user) throws NoSuchAlgorithmException {
        String sha256Password = DigestUtils.sha256Hex(user.getPassword());
        user.setPassword(sha256Password);
    }

    public OptionalInt findIdOfAccount(String username) {
        return userDao.findUser(username);
    }
}
