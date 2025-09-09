package com.urise.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlTranzaction<T> {
    T execute(Connection conn) throws SQLException;
}
