package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

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
