package io.github.mateusznk.webankapp.domain.payment;

import io.github.mateusznk.webankapp.domain.common.BaseDao;
import io.github.mateusznk.webankapp.logs.WriteExceptionsToFile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDao extends BaseDao {
    private final WriteExceptionsToFile writeExceptionsToFile = new WriteExceptionsToFile();

    public void sendPayment(String senderAccount, String receiverAccount, double amount) {
        subAmountFromSenderAccount(senderAccount, amount);
        addAmountToReceiverAccount(receiverAccount, amount);
    }


    private void addAmountToReceiverAccount(String receiverAccount, double amount) {
        final String query = """
                UPDATE
                    account
                SET
                    balance = ?
                WHERE
                    account_number = ?
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, getActualBalance(receiverAccount) + amount);
            preparedStatement.setString(2, receiverAccount);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            writeExceptionsToFile.typicalErrorLog(getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }

    private double getActualBalance(String accountNumber) {
        final String query = """
                SELECT
                    balance
                FROM
                    account
                WHERE
                    account_number = ?
                """;

        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(accountNumber);
            if (resultSet.next()) {
                return resultSet.getDouble("balance");
            } else {
                throw  new SQLException();
            }
        } catch (SQLException e) {
            writeExceptionsToFile.typicalErrorLog(getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }

    private void subAmountFromSenderAccount(String senderAccount, double amount) {
        final String query = """
                UPDATE
                    account
                SET
                    balance = ?
                WHERE
                    account_number = ?
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, getActualBalance(senderAccount) - amount);
            preparedStatement.setString(2, senderAccount);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            writeExceptionsToFile.typicalErrorLog(getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }
}
