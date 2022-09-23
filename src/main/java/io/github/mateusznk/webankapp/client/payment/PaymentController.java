package io.github.mateusznk.webankapp.client.payment;

import io.github.mateusznk.webankapp.domain.api.*;
import io.github.mateusznk.webankapp.logs.WriteExceptionsToFile;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.OptionalInt;

@WebServlet("/payment")
public class PaymentController extends HttpServlet {
    private final AccountService accountService = new AccountService();
    private final UserService userService = new UserService();
    private final WriteExceptionsToFile writeExceptionsToFile = new WriteExceptionsToFile();

    private final PaymentService paymentService = new PaymentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = getIdFromDB(request);
        Optional<AccountBasicInfo> accountBasicInfo = accountService.readAccountData(id);
        if (accountBasicInfo.isEmpty()) {
            exceptionToLog(Thread.currentThread().getStackTrace()[1].getLineNumber());
            throw new RuntimeException();
        }

        request.setAttribute("account_number", accountBasicInfo.get().getAccountNumber());
        request.setAttribute("account_balance", accountBasicInfo.get().getBalance());
        request.getRequestDispatcher("/WEB-INF/views/payment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.getRequestDispatcher("/WEB-INF/views/payment.jsp").forward(request, response);
        PaymentBasicInfo paymentBasicInfo = getDataFromRequest(request);
        paymentService.payment(paymentBasicInfo);
        response.sendRedirect(request.getContextPath() + "/payment");
    }

    private int getIdFromDB(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        OptionalInt id = userService.findIdOfAccount(username);
        if (id.isEmpty()) {
            exceptionToLog(Thread.currentThread().getStackTrace()[1].getLineNumber());
            throw new RuntimeException();
        }
        return id.getAsInt();
    }

    private PaymentBasicInfo getDataFromRequest(HttpServletRequest request) {
        String senderAccount = request.getParameter("account").replaceAll("\\s+", "");
        String receiverAccount = request.getParameter("receiver-account").replaceAll("\\s+", "");
        double amount = Double.parseDouble(request.getParameter("amount"));
        return new PaymentBasicInfo(
                senderAccount,
                receiverAccount,
                amount
        );
    }

    private void exceptionToLog(int lineNumber) {
        writeExceptionsToFile.unusualErrorLog(lineNumber,
                getClass().getName());
    }
}
