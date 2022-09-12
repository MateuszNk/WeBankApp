package io.github.mateusznk.webankapp.client.account;

import io.github.mateusznk.webankapp.domain.api.AccountBasicInfo;
import io.github.mateusznk.webankapp.domain.api.AccountService;
import io.github.mateusznk.webankapp.domain.api.UserService;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OptionalInt id = getIdFromDB(request);
        if (id.isEmpty()) {
            throw new UnknownError();
        }
        Optional<AccountBasicInfo> accountBasicInfo = accountService.readAccountData(id.getAsInt());
        if (accountBasicInfo.isEmpty()){
            throw new UnknownError();
        }
        
        request.setAttribute("account_number", accountBasicInfo.get().getAccountNumber());
        request.setAttribute("account_balance", accountBasicInfo.get().getBalance());
        request.getRequestDispatcher("/WEB-INF/views/account.jsp").forward(request, response);
    }

    private OptionalInt getIdFromDB(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        return userService.findIdOfAccount(username);
    }
}