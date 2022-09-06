package io.github.mateusznk.webankapp.domain.news;

import io.github.mateusznk.webankapp.config.DataSourceProvider;
import io.github.mateusznk.webankapp.domain.common.BaseDao;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewsDao extends BaseDao {

    public List<News> findAllNews() {
        final String query = """
                SELECT
                id, title, url, description, date_added
                FROM
                news
                """;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(query);
            List<News> allNews = new ArrayList<>();
            while (resultSet.next()) {
                News news = mapRow(resultSet);
                allNews.add(news);
            }
            return allNews;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static News mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String url = resultSet.getString("url");
        String description = resultSet.getString("description");
        LocalDateTime dateAdded = resultSet.getTimestamp("date_added").toLocalDateTime();
        return new News(id, title, url, description, dateAdded);
    }
}
