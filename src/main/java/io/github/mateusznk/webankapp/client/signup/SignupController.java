package io.github.mateusznk.webankapp.client.signup;

import io.github.mateusznk.webankapp.domain.api.UserRegistration;
import io.github.mateusznk.webankapp.domain.api.UserService;
import io.github.mateusznk.webankapp.domain.user.User;
import io.github.mateusznk.webankapp.errors.checkErrors.SignupErrors;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/signup")
public class SignupController extends HttpServlet {
    private final UserService userService = new UserService();
    private final SignupErrors signupErrors = new SignupErrors();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRegistration userRegistration = getUserData(request);
        if (signupErrors.isError(request, userRegistration)) {
            request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
        } else {
            userService.register(userRegistration);
            response.sendRedirect(request.getContextPath());
        }

    }

    private UserRegistration getUserData(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone_number");
        boolean newsletter = request.getParameter("newsletter") != null;

        return new UserRegistration(username, password, email, phoneNumber, newsletter);
    }
}
