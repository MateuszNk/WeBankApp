package io.github.mateusznk.webankapp.domain.country;

import io.github.mateusznk.webankapp.domain.account.Account;
import io.github.mateusznk.webankapp.domain.common.BaseDao;
import io.github.mateusznk.webankapp.logs.WriteExceptionsToFile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CountryDao extends BaseDao {
    private final WriteExceptionsToFile writeExceptionsToFile = new WriteExceptionsToFile();
    public List<Country> findAllCountries() {
        final String query = """
                SELECT
                    id, country
                FROM
                    countries
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            List<Country> allCountries = new ArrayList<>();
            while (resultSet.next()) {
                Country country = mapRow(resultSet);
                allCountries.add(country);
            }
            return allCountries;
        } catch (SQLException e) {
            writeExceptionsToFile.typicalErrorLog(getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }

    private Country mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String country = resultSet.getString("country");
        return new Country(id, country);
    }
}
