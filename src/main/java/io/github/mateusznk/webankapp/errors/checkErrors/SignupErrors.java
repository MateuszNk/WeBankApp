package io.github.mateusznk.webankapp.errors.checkErrors;

import io.github.mateusznk.webankapp.domain.api.UserRegistration;
import io.github.mateusznk.webankapp.domain.api.UserService;
import io.github.mateusznk.webankapp.domain.common.BaseDao;
import io.github.mateusznk.webankapp.errors.Errors;
import io.github.mateusznk.webankapp.errors.TypesOfErrors;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SignupErrors extends BaseDao {
    public boolean isError(HttpServletRequest request, UserRegistration userRegistration) {
        List<String> errors = new ArrayList<>();
        String error = isUsernameTaken(userRegistration.getUsername());
        errors.add(error);

        error = isUsernameValid(userRegistration.getUsername());
        errors.add(error);

        error = checkIfPasswordsAreEqual(userRegistration.getPassword(),
                request.getParameter("repeat-password"));
        errors.add(error);

        error = passwordRequirements(userRegistration.getPassword());
        errors.add(error);

        error = checkEmail(userRegistration.getEmail());
        errors.add(error);

        error = checkIfConditionsAreAccepted(request.getParameter("gdpr") != null);
        errors.add(error);

        for (String err: errors) {
            if (!err.isEmpty()) {
                request.setAttribute("isError", true);
                request.setAttribute("error", err);
                return true;
            }
        }

        return false;
    }

    private String isUsernameTaken(String username) {
        final String query = """
                SELECT
                    username
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
                System.out.println("User found!");
                return Errors.returnStringError(TypesOfErrors.USERNAME_IS_TAKEN);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

    private String isUsernameValid(String username) {
        final Pattern specialCharacterPattern = Pattern.compile("[^a-z0-9]",
                Pattern.CASE_INSENSITIVE);
        final int minimalLength = 6;
        final int maximalLength = 16;
        if (specialCharacterPattern.matcher(username).find() ||
        username.contains(" ") ||
        username.length() < minimalLength ||
        username.length() > maximalLength) {
            return Errors.returnStringError(TypesOfErrors.USERNAME_IS_NOT_VALID);
        }

        return "";
    }

    private String checkIfPasswordsAreEqual(String password, String repeatedPassword) {
        if (password.equals(repeatedPassword)) {
            return "";
        } else {
            return Errors.returnStringError(TypesOfErrors.GIVEN_PASSWORDS_DO_NOT_MATCH);
        }
    }

    private String passwordRequirements(String password) {
        final Pattern specialCharacterPattern = Pattern.compile("[^a-z0-9]",
                Pattern.CASE_INSENSITIVE);
        final Pattern upperCasePattern = Pattern.compile("[A-Z]");
        final Pattern lowerCasePattern = Pattern.compile("[a-z]");
        final Pattern digitCasePattern = Pattern.compile("[0-9]");
        final int minimalLength = 8;
        final int maximalLength = 32;
        if (password.length() < minimalLength ||
                password.length() > maximalLength ||
                !specialCharacterPattern.matcher(password).find() ||
                !upperCasePattern.matcher(password).find() ||
                !lowerCasePattern.matcher(password).find() ||
                !digitCasePattern.matcher(password).find()) {
            return Errors.returnStringError(TypesOfErrors.PASSWORD_NOT_MEET_REQUIREMENTS);
        }

        return "";
    }

    private String checkEmail(String email) {
        final long count = email.chars().filter(ch -> ch == '@').count();
        final int minimalLength = 8;
        final int maximalLength = 60;
        if (count > 1 ||
                email.length() < minimalLength ||
                email.length() > maximalLength ||
                !email.contains("@") ||
                !email.contains(".com")) {
            return Errors.returnStringError(TypesOfErrors.EMAIL_IS_NOT_VALID);
        }
        return "";
    }

    private String checkIfConditionsAreAccepted(boolean conditions) {
        if (!conditions) {
            return Errors.returnStringError(TypesOfErrors.CONDITIONS_NOT_ACCEPTED);
        }
        return "";
    }
}