package io.github.mateusznk.webankapp.client.account.personal;

import io.github.mateusznk.webankapp.domain.api.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

@WebServlet("/personal-data")
@ServletSecurity(
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "GET", rolesAllowed = "USER"),
                @HttpMethodConstraint(value = "POST", rolesAllowed = "USER")
        }
)
public class PersonalDataController extends HttpServlet {
    private final CountryService countryService = new CountryService();
    private final PersonalDataService personalDataService = new PersonalDataService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CountryName> countries = countryService.findAllCategoryNames();
        OptionalInt optionalInt = getIdFromDB(request);
        if (optionalInt.isEmpty()) {
            throw new UnknownError();
        }
        int id = optionalInt.getAsInt();
        PersonalDataBasicInfo personalDataBasicInfo = personalDataService.getPersonalData(id);
        if (personalDataBasicInfo != null) {
            List<PersonalDataBasicInfo> list = new ArrayList<>();
            list.add(personalDataBasicInfo);
            request.setAttribute("name", personalDataBasicInfo.getName());
            request.setAttribute("data", list);
            request.setAttribute("is_personal_data", true);
        } else {
            request.setAttribute("is_personal_data", false);
        }

        request.setAttribute("countries", countries);
        request.getRequestDispatcher("/WEB-INF/views/personal-data.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonalDataBasicInfo personalDataBasicInfo = createSaveRequest(request);
        OptionalInt optionalInt = getIdFromDB(request);
        if (optionalInt.isEmpty()) {
            throw new UnknownError();
        }

        personalDataService.checkIfPersonalDataExists(personalDataBasicInfo,
                optionalInt.getAsInt(),
                getIntCountry(request));
        response.sendRedirect(request.getContextPath());
        //request.getRequestDispatcher("/account").forward(request, response);
    }

    private PersonalDataBasicInfo createSaveRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        java.sql.Date SQLDate;
        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birth_date"));
            SQLDate = new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String city = request.getParameter("city");
        String postalCode = request.getParameter("postal_code");
        String address = request.getParameter("address");
        int countryId = getIntCountry(request);
        String country = personalDataService.getStringCountryFromCountryID(countryId);

        return new PersonalDataBasicInfo(
                name,
                surname,
                SQLDate,
                city,
                postalCode,
                address,
                country
        );
    }

    private int getIntCountry(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("countryId"));
    }

    private OptionalInt getIdFromDB(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        return userService.findIdOfAccount(username);
    }
}
