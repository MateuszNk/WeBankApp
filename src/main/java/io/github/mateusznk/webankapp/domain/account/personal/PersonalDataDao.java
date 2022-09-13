package io.github.mateusznk.webankapp.domain.account.personal;

import io.github.mateusznk.webankapp.domain.common.BaseDao;

import java.sql.*;

public class PersonalDataDao extends BaseDao {

    public void saveDataToDatabase(PersonalData personalData) {
        /*
         * Trzeba przerobic z SELECT id FROM registration_data WHERE id = ?
         */
        final String query = """
                INSERT INTO
                    personal_data (name, surname, birth_date, city, postal_code, address, country)
                VALUES
                    (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, personalData.getName());
            preparedStatement.setString(2, personalData.getSurname());
            preparedStatement.setObject(3, personalData.getBirthDate());
            preparedStatement.setString(4, personalData.getCity());
            preparedStatement.setString(5, personalData.getPostalCode());
            preparedStatement.setString(6, personalData.getAddress());
            preparedStatement.setString(7, personalData.getCountry());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                personalData.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
