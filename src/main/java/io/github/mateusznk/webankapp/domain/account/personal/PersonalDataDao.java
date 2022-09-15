package io.github.mateusznk.webankapp.domain.account.personal;

import io.github.mateusznk.webankapp.domain.api.PersonalDataBasicInfo;
import io.github.mateusznk.webankapp.domain.common.BaseDao;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PersonalDataDao extends BaseDao {

    public void saveData(PersonalData personalData, int id) {

        saveDataToDatabase(personalData, id);
    }

    private void saveDataToDatabase(PersonalData personalData, int id) {
        final String query = """
                INSERT INTO
                    personal_data (id, name, surname, birth_date, city, postal_code, address, country)
                VALUES
                    ((SELECT id FROM registration_data WHERE id = ?), ?, ?, ?, ?, ?, ?, (SELECT country FROM countries WHERE country = ?))
                """;

        Date SQLDate = transformDateToSQL(String.valueOf(personalData.getBirthDate()));
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, personalData.getName());
            preparedStatement.setString(3, personalData.getSurname());
            preparedStatement.setObject(4, personalData.getBirthDate());
            preparedStatement.setString(5, personalData.getCity());
            preparedStatement.setString(6, personalData.getPostalCode());
            preparedStatement.setString(7, personalData.getAddress());
            preparedStatement.setString(8, personalData.getCountry());
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

    public void replaceData (PersonalDataBasicInfo personalDataBasicInfo, int id) {
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

        Date SQLDate = transformDateToSQL(String.valueOf(personalDataBasicInfo.getBirthDate()));
        //String country = getCountryWithId(idOfCountry);
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, personalDataBasicInfo.getName());
            preparedStatement.setString(2, personalDataBasicInfo.getSurname());
            preparedStatement.setObject(3, personalDataBasicInfo.getBirthDate());
            preparedStatement.setString(4, personalDataBasicInfo.getCity());
            preparedStatement.setString(5, personalDataBasicInfo.getPostalCode());
            preparedStatement.setString(6, personalDataBasicInfo.getAddress());
            preparedStatement.setString(7, personalDataBasicInfo.getCountry());
            preparedStatement.setInt(8, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PersonalDataBasicInfo readRecordWithId(int id) {
        final String query = """
                SELECT
                    name, surname, birth_date, city, postal_code, address, country
                FROM
                    personal_data
                WHERE
                    id = ?
                """;
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return map(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static PersonalDataBasicInfo map(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        Date birthDate = resultSet.getDate("birth_date");
        String city = resultSet.getString("city");
        String postalCode = resultSet.getString("postal_code");
        String address = resultSet.getString("address");
        String country = resultSet.getString("country");
        return new PersonalDataBasicInfo(name, surname, birthDate, city, postalCode, address, country);
    }
    private java.sql.Date transformDateToSQL(String stringDate) {
        java.sql.Date SQLDate;
        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
            SQLDate = new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return SQLDate;
    }

    public int getCountryIdFromString(String country) {
        final String query = """
                SELECT
                    id
                FROM
                    countries
                WHERE
                    country = ?
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, country);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new SQLException();
            }
            return resultSet.getInt("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
