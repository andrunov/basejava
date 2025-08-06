package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutor<R> {
    R execute(PreparedStatement preparedStatement) throws SQLException;
}
