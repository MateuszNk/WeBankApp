package io.github.mateusznk.webankapp.domain.account;

import io.github.mateusznk.webankapp.domain.common.BaseDao;
import io.github.mateusznk.webankapp.logs.WriteExceptionsToFile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AccountDao extends BaseDao {

    private final WriteExceptionsToFile writeExceptionsToFile = new WriteExceptionsToFile();
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
                return Optional.of(mapRow(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            writeExceptionsToFile.typicalErrorLog(getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }

    private static Account mapRow(ResultSet resultSet) throws SQLException {
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
            preparedStatement.setString(2, generateAccountNumber());
            preparedStatement.setDouble(3,0.0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            writeExceptionsToFile.typicalErrorLog(getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }

    private String generateAccountNumber() {
        return generateAccount(readAllAccounts());
    }

    private List<Account> readAllAccounts() {
        final String query = """
                SELECT
                    id, account_number, balance
                FROM
                    account
                """;

        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery(query);
            List<Account> allAccounts = new ArrayList<>();
            while (resultSet.next()) {
                allAccounts.add(mapRow(resultSet));
            }
            return allAccounts;
        } catch (SQLException e) {
            writeExceptionsToFile.typicalErrorLog(getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }

    private String generateAccount(List<Account> accounts) {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        boolean accountNumberExists = true;
        do {
            accountNumber.delete(0, accountNumber.length());
            for (int i = 0; i < 26; i++) {
                int number = random.nextInt(10);
                accountNumber.append(number);
                accountNumberExists = checkIfAccountNumberExists(accountNumber.toString(), accounts);
            }
        } while (accountNumberExists);

        return accountNumber.toString();
    }

    private boolean checkIfAccountNumberExists(String accountNumber, List<Account> accounts) {
        for (Account account : accounts) {
            if (accountNumber.equals(account.getAccountNumber())) {
                return true;
            }
        }
        return false;
    }
}
