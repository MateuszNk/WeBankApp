package io.github.mateusznk.webankapp.errors;

import io.github.mateusznk.webankapp.logs.WriteExceptionsToFile;

public class Errors {

    public static String returnStringError(TypesOfErrors typeOfError) {
        switch (typeOfError) {
            case USERNAME_IS_TAKEN -> {
                return "Username is already taken";
            } case USERNAME_IS_NOT_VALID -> {
                return """
                        Username is not valid!<br>
                        *) Minimum 6 characters<br>
                        *) Maximum 16 characters<br>
                        *) Without white characters ex.: 'space'<br>
                        *) Without special characters ex.: '*'
                        """;
            } case PASSWORD_NOT_MEET_REQUIREMENTS -> {
                return """
                        Password does not meet the complexity requirements!<br>
                        *) Maximum 32 characters<br>
                        *) Minimum 8 characters<br>
                        *) Minimum 1 digit<br>
                        *) Minimum 1 upper case letter<br>
                        *) Minimum 1 lower case letter<br>
                        *) Minimum 1 special character<br>
                        *) Without white characters ex.: 'space'
                        """;
            } case GIVEN_PASSWORDS_DO_NOT_MATCH -> {
                return "Given passwords do not match!";
            } case EMAIL_IS_NOT_VALID -> {
                return """
                        E-mail address is not valid!<br>
                        *) Maximum 32 characters<br>
                        *) Maximum ONE '@' character<br>
                        *) Minimum one domain ex.: '.com'<br>
                        *) Without white characters ex.: 'space'
                        """;
            } case CONDITIONS_NOT_ACCEPTED -> {
                return "Conditions not accepted!";
            } case USER_TOO_YOUNG -> {
                return "You are too young for this account!";
            } default -> {
                return "Critical error!";
            }
        }
    }
}