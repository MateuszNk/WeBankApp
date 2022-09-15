package io.github.mateusznk.webankapp.client.account;

import io.github.mateusznk.webankapp.domain.api.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.OptionalInt;

@WebServlet("/account")
public class AccountController extends HttpServlet {
    private final AccountService accountService = new AccountService();
    private final UserService userService = new UserService();
    private final PersonalDataService personalDataService = new PersonalDataService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = getIdFromDB(request);
        PersonalDataBasicInfo personalDataBasicInfo = personalDataService.getPersonalData(id);
        if (personalDataBasicInfo != null) {
            String fullName = personalDataBasicInfo.getName() + " " + personalDataBasicInfo.getSurname();
            request.setAttribute("user_name", fullName);
            request.setAttribute("is_personal_data", true);
        } else {
            request.setAttribute("is_personal_data", false);
        }

        Optional<AccountBasicInfo> accountBasicInfo = accountService.readAccountData(id);
        if (accountBasicInfo.isEmpty()) {
            throw new UnknownError();
        }
        request.setAttribute("account_number", accountBasicInfo.get().getAccountNumber());
        request.setAttribute("account_balance", accountBasicInfo.get().getBalance());
        request.getRequestDispatcher("/WEB-INF/views/account.jsp").forward(request, response);
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