package io.github.mateusznk.webankapp.domain.common;

import io.github.mateusznk.webankapp.config.DataSourceProvider;
import io.github.mateusznk.webankapp.logs.WriteExceptionsToFile;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class BaseDao {
    private final DataSource dataSource;
    private final WriteExceptionsToFile writeExceptionsToFile = new WriteExceptionsToFile();
    public BaseDao() {
        try {
            this.dataSource = DataSourceProvider.getDataSource();
        } catch (NamingException e) {
            writeExceptionsToFile.typicalErrorLog(getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            writeExceptionsToFile.typicalErrorLog(getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }
}
