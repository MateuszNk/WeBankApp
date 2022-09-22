package io.github.mateusznk.webankapp.errors.checkErrors;

import io.github.mateusznk.webankapp.domain.api.PersonalDataBasicInfo;
import io.github.mateusznk.webankapp.errors.Errors;
import io.github.mateusznk.webankapp.errors.TypesOfErrors;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class PersonalDataErrors {

    public boolean isError(HttpServletRequest request, PersonalDataBasicInfo personalDataBasicInfo) {
        String error = checkIfUserIsTooYoung(personalDataBasicInfo.getBirthDate());

        if (!error.isEmpty()) {
            request.setAttribute("isError", true);
            request.setAttribute("error", error);
            return true;
        }

        return false;
    }

    private String checkIfUserIsTooYoung(Date birthDate) {
        LocalDate newDate = new java.sql.Date(birthDate.getTime()).toLocalDate();
        if (Period.between(newDate, LocalDate.now()).getYears() < 18) {
            return Errors.returnStringError(TypesOfErrors.USER_TOO_YOUNG);
        } else {
            return "";
        }
    }
}
