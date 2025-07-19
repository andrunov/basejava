package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlExecutor;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.executeSQL(
                null,
                "DELETE FROM resume",
                connectionFactory,
                (parameter, ps) -> {
                    ps.execute();
                    return null;
                });
    }

    @Override
    public Resume get(String uuid) {
        return SqlHelper.executeSQL(
                uuid,
                "SELECT * FROM resume r WHERE r.uuid =?",
                connectionFactory,
                (parameter, ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                });
    }

    @Override
    public void update(Resume resume) {
        SqlHelper.executeSQL(
                resume,
                "UPDATE resume SET full_name = ? WHERE uuid = ?",
                connectionFactory,
                (parameter, ps) -> {
                    ps.setString(1, resume.getFullName());
                    ps.setString(2, resume.getUuid());
                    int rowsUpdated = ps.executeUpdate();
                    if (rowsUpdated == 0) {
                        throw new NotExistStorageException("Resume " + resume.getUuid() + " not found");
                    }
                    return null;
                });
    }

    @Override
    public void save(Resume resume) {
        SqlHelper.executeSQL(
                resume,
                "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                connectionFactory,
                new SqlExecutor<Resume, Object>() {
                    @Override
                    public Object execute(Resume parameter, PreparedStatement ps) throws SQLException {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                        return null;
                    }
                });
    }

    @Override
    public void delete(String uuid) {
        SqlHelper.executeSQL(
                uuid,
                "DELETE FROM resume WHERE uuid = ?",
                connectionFactory,
                (parameter, ps) -> {
                    ps.setString(1, uuid);
                    int rowsDeleted = ps.executeUpdate();
                    if (rowsDeleted == 0) {
                        throw new StorageException("Resume " + uuid + " not found", uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        String sql = "SELECT * FROM resume ORDER BY full_name, uuid";

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                resumes.add(new Resume(uuid.trim(), fullName.trim()));
            }
        } catch (SQLException e) {
            throw new StorageException("Failed to get all resumes", e);
        }

        return resumes;
    }

    @Override
    public int size() {
        String sql = "SELECT COUNT(*) FROM resume";

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);  // Получаем значение первого (и единственного) столбца
            }
            return 0;  // На случай, если результат пуст (маловероятно для COUNT)
        } catch (SQLException e) {
            throw new StorageException("Failed to get resume count", e);
        }
    }
}
