package io.github.mateusznk.webankapp.domain.account.personal;

import io.github.mateusznk.webankapp.domain.api.PersonalDataBasicInfo;
import io.github.mateusznk.webankapp.domain.common.BaseDao;

import java.sql.*;

public class PersonalDataDao extends BaseDao {

    public void saveData(PersonalData personalData, int id, int idOfCountry) {
        String country = getCountryWithId(idOfCountry);
        saveDataToDatabase(personalData, id ,country);
    }

    private void saveDataToDatabase(PersonalData personalData, int id, String country) {
        final String query = """
                INSERT INTO
                    personal_data (id, name, surname, birth_date, city, postal_code, address, country)
                VALUES
                    ((SELECT id FROM registration_data WHERE id = ?), ?, ?, ?, ?, ?, ?, (SELECT country FROM countries WHERE country = ?))
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, personalData.getName());
            preparedStatement.setString(3, personalData.getSurname());
            preparedStatement.setObject(4, personalData.getBirthDate());
            preparedStatement.setString(5, personalData.getCity());
            preparedStatement.setString(6, personalData.getPostalCode());
            preparedStatement.setString(7, personalData.getAddress());
            preparedStatement.setString(8, country);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCountryWithId (int idOfCountry) {
        final String query = """
                SELECT
                    country
                FROM
                    countries
                WHERE
                    id = ?
                """;

        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idOfCountry);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new SQLException();
            }
            return resultSet.getString("country");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRecordInDb(int id) {
        final String query = """
                SELECT
                    id, name, surname, birth_date, city, postal_code, address, country
                FROM
                    personal_data
                WHERE
                    id = ?
                """;

        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void replaceData (PersonalDataBasicInfo personalDataBasicInfo,
                             int id,
                             Integer idOfCountry) {
        final String query = """
                UPDATE
                    personal_data
                SET
                    name = ?,
                    surname = ?,
                    birth_date = ?,
                    city = ?,
                    postal_code = ?,
                    address = ?,
                    country = (SELECT country FROM countries WHERE country = ?)
                WHERE
                    id = ?
                """;

        String country = getCountryWithId(idOfCountry);
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, personalDataBasicInfo.getName());
            preparedStatement.setString(2, personalDataBasicInfo.getSurname());
            preparedStatement.setObject(3, personalDataBasicInfo.getBirthDate());
            preparedStatement.setString(4, personalDataBasicInfo.getCity());
            preparedStatement.setString(5, personalDataBasicInfo.getPostalCode());
            preparedStatement.setString(6, personalDataBasicInfo.getAddress());
            preparedStatement.setString(7, country);
            preparedStatement.setInt(8, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
