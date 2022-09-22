package io.github.mateusznk.webankapp.domain.user;

import io.github.mateusznk.webankapp.domain.common.BaseDao;
import io.github.mateusznk.webankapp.logs.WriteExceptionsToFile;

import java.sql.*;
import java.util.OptionalInt;

public class UserDao extends BaseDao {

    private final WriteExceptionsToFile writeExceptionsToFile = new WriteExceptionsToFile();

    public void save(User user) {
        saveUser(user);
        saveUserRole(user);
    }

    private void saveUser(User user) {
        final String query = """
                INSERT INTO
                registration_data (username, password, email, phone_number, registration_date, newsletter)
                VALUES
                (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setObject(5, user.getRegistrationDate());
            preparedStatement.setBoolean(6, user.getNewsletter());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            writeExceptionsToFile.typicalErrorLog(getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }

    private void saveUserRole(User user) {
        final String query = """
                INSERT INTO
                user_role (username)
                VALUES
                (?)
                """;

        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            writeExceptionsToFile.typicalErrorLog(getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }

    public OptionalInt findUser(String username) {
        return returnIdOfUser(username);
    }

    private OptionalInt returnIdOfUser(String username) {
        final String query = """
                SELECT
                    id
                FROM
                    registration_data
                WHERE
                    username = ?
                """;

        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return OptionalInt.of(resultSet.getInt("id"));
            }
            return OptionalInt.empty();
        } catch (SQLException e) {
            writeExceptionsToFile.typicalErrorLog(getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }
}