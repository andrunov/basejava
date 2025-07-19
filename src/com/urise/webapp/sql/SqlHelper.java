package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private static  PreparedStatement createStatement(
            String sqlExpression,
            ConnectionFactory connectionFactory) throws SQLException {

        Connection conn = connectionFactory.getConnection();
        return conn.prepareStatement(sqlExpression);
    }

    public  static <R, P> R executeWithException(
            P parameter,
            String sqlExpression,
            ConnectionFactory connectionFactory,
            SqlExecutor<P, R> executor) {

        try (PreparedStatement ps = createStatement(sqlExpression, connectionFactory)) {
            return executor.execute(parameter, ps);

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public  static <R, P> R executeSQL(
                                   P parameter,
                                   String sqlExpression,
                                   ConnectionFactory connectionFactory,
                                   SqlExecutor<P, R> executor) {

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlExpression)) {

            return executor.execute(parameter, ps);

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
