package io.github.mateusznk.webankapp.client.account;

import io.github.mateusznk.webankapp.domain.account.AccountDao;
import io.github.mateusznk.webankapp.domain.api.AccountBasicInfo;
import io.github.mateusznk.webankapp.domain.api.AccountService;
import io.github.mateusznk.webankapp.domain.api.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.OptionalInt;

@WebServlet("/account")
public class AccountController extends HttpServlet {
    private final AccountService accountService = new AccountService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountBasicInfo accountBasicInfo = accountService.readAccountData(getIdFromDB(request).getAsInt());
        request.setAttribute("account_number", accountBasicInfo.getAccountNumber());
        request.setAttribute("account_balance", accountBasicInfo.getBalance());
        request.getRequestDispatcher("/WEB-INF/views/account.jsp").forward(request, response);
    }

    private OptionalInt getIdFromDB(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        System.out.println("Username from request: " + username);

        return userService.findIdOfAccount(username);
    }
}