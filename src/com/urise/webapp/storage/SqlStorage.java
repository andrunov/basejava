package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.execute(
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
        return SqlHelper.execute(
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
        SqlHelper.execute(
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
    SqlHelper.execute(
         resume,
        "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
         connectionFactory,
            (parameter, ps) -> {
               ps.setString(1, resume.getUuid());
               ps.setString(2, resume.getFullName());
               try {
                     ps.execute();
                     return null;
               } catch (SQLException e) {
                     throw new ExistStorageException(e.getMessage());
               }
           });
    }

    @Override
    public void delete(String uuid) {
        SqlHelper.execute(
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
        return SqlHelper.execute(
           null,
           "SELECT * FROM resume ORDER BY full_name, uuid",
            connectionFactory,
                (parameter, ps) -> {
                     List<Resume> resumes = new ArrayList<>();
                     ResultSet rs = ps.executeQuery();
                     while (rs.next()) {
                         String uuid = rs.getString("uuid");
                         String fullName = rs.getString("full_name");
                         resumes.add(new Resume(uuid.trim(), fullName.trim()));
                     }
                     rs.close();
                     return resumes;
                });
    }

    @Override
    public int size() {
        return SqlHelper.execute(
                null,
                "SELECT COUNT(*) FROM resume",
                connectionFactory,
                (parameter, ps) -> {
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        return rs.getInt(1);  // Получаем значение первого (и единственного) столбца
                    }
                    return 0;  // На случай, если результат пуст
                }
        );
    }
}
