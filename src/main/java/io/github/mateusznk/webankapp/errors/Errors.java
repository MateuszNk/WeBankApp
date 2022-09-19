package io.github.mateusznk.webankapp.errors;

public class Errors {

    public static String returnStringError(TypesOfErrors typeOfError) {
        switch (typeOfError) {
            case USERNAME_IS_TAKEN -> {
                return "Username is already taken";
            } case PASSWORD_NOT_MEET_REQUIREMENTS -> {
                return """
                        Password does not meet the complexity requirements!
                        *) Minimum 8 characters
                        *) Minimum 1 digit
                        *) Minimum 1 upper case letter
                        *) Minimum 1 lower case letter
                        *) Minimum 1 special character
                        """;
            } case GIVEN_PASSWORDS_DO_NOT_MATCH -> {
                return "Given passwords do not match!";
            } case EMAIL_IS_NOT_VALID -> {
                return "E-mail address is not valid!";
            } case CONDITIONS_NOT_ACCEPTED -> {
                return "Conditions not accepted!";
            } case WRONG_LOGIN_ANDOR_PASSWORD -> {
                return "Wrong login and/or password!";
            } default -> {
                return "Critical error!";
            }
        }
    }
}
