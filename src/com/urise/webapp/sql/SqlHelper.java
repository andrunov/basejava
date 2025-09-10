package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class SqlHelper {

    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public  <R> R execute(String sqlExpression, SqlExecutor<R> executor) throws RuntimeException {

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlExpression)) {

            return executor.execute(ps);

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(e.getMessage());
            } else {
                throw new StorageException(e);
            }
        }
    }

    public <T> T transactionalExecute(SqlTranzaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public void executeSections(Resume resume, PreparedStatement ps) throws SQLException {
        for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, e.getKey().name());
            ps.setString(3, e.getValue());
            ps.addBatch();
        }
        ps.executeBatch();
    }

}
