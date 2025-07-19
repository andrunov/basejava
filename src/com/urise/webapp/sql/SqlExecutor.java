package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutor<P, R> {
    R execute(P parameter, PreparedStatement preparedStatement) throws SQLException;
}
