package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public  <R, P> R execute(
                                   P parameter,
                                   String sqlExpression,
                                   SqlExecutor<P, R> executor) {

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlExpression)) {

            return executor.execute(parameter, ps);

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
