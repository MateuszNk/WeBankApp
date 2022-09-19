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
        request.setAttribute("countries", countries);

        PersonalDataBasicInfo personalDataBasicInfo = personalDataService.getPersonalData(getIdFromDB(request));
        if (personalDataBasicInfo != null) {
            List<PersonalDataBasicInfo> personalDataList = new ArrayList<>();
            personalDataList.add(personalDataBasicInfo);
            request.setAttribute("data", personalDataList);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String format = formatter.format(personalDataBasicInfo.getBirthDate());
            request.setAttribute("format", format);
            request.setAttribute("is_personal_data", true);
        } else {
            request.setAttribute("is_personal_data", false);
        }

        request.getRequestDispatcher("/WEB-INF/views/personal-data.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PersonalDataBasicInfo personalDataBasicInfo = createSaveRequest(request);
        personalDataService.checkIfPersonalDataExists(personalDataBasicInfo, getIdFromDB(request));
        response.sendRedirect(request.getContextPath() + "/account");
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
        String country = request.getParameter("countryId");
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

    private int getIdFromDB(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        OptionalInt id = userService.findIdOfAccount(username);
        if (id.isEmpty()) {
            throw new UnknownError();
        }
        return id.getAsInt();
    }
}
