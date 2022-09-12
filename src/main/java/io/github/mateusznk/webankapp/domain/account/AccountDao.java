package io.github.mateusznk.webankapp.domain.account;

import io.github.mateusznk.webankapp.domain.common.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AccountDao extends BaseDao {

    public Optional<Account> readAccountDataWithId(int id) {
        final String query = """
                SELECT
                    id, account_number, balance
                FROM
                    account
                WHERE
                    id = ?
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(map(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Account map(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String accountNumber = resultSet.getString("account_number");
        double balance = resultSet.getDouble("balance");
        return new Account(id, accountNumber, balance);
    }

    public void createNewAccount(Integer id) {
        final String query = """
                INSERT INTO
                    account (id, account_number, balance)
                VALUES
                    ((SELECT id FROM registration_data WHERE id = ?), ?, ?)
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, "66123412341234123412341234");
            preparedStatement.setDouble(3,0.0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
