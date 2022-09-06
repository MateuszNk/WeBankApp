package io.github.mateusznk.webankapp.client.home;

import io.github.mateusznk.webankapp.domain.api.NewsBasicInfo;
import io.github.mateusznk.webankapp.domain.api.NewsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("")
public class HomeController extends HttpServlet {
    private final NewsService newsService = new NewsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<NewsBasicInfo> news = newsService.findAll();
        request.setAttribute("news", news);
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
}
