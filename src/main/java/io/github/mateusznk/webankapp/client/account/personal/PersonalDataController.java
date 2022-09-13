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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
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
        personalDataService.createNewPersonalData(personalDataBasicInfo,
                optionalInt.getAsInt(),
                getIntCountry(request));
        response.sendRedirect(request.getContextPath());
    }

    private PersonalDataBasicInfo createSaveRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd[HH]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .toFormatter();
        String date = request.getParameter("birth_date");
        LocalDateTime birthDate = LocalDateTime.parse(date, formatter);
        String city = request.getParameter("city");
        String postalCode = request.getParameter("postal_code");
        String address = request.getParameter("address");
        int countryId = getIntCountry(request);
        String country = personalDataService.getStringCountryFromCountryID(countryId);

        return new PersonalDataBasicInfo(
                name,
                surname,
                birthDate,
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
