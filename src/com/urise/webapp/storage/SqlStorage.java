package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {


    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.execute(
                null,
                "DELETE FROM resume",
                (parameter, ps) -> {
                    ps.execute();
                    return null;
                });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(
                null,
                "SELECT * FROM resume r WHERE r.uuid =?",
                (parameter, ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume result = new Resume(uuid, rs.getString("full_name"));
                    return result;
                });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.execute(
                null,
                "UPDATE resume SET full_name = ? WHERE uuid = ?",
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
    sqlHelper.execute(
         null,
        "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
            (parameter, ps) -> {
               ps.setString(1, resume.getUuid());
               ps.setString(2, resume.getFullName());
               ps.execute();
               return null;
           });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute(
                null,
                "DELETE FROM resume WHERE uuid = ?",
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
        return sqlHelper.execute(
           null,
           "SELECT * FROM resume ORDER BY full_name, uuid",
                (parameter, ps) -> {
                     List<Resume> resumes = new ArrayList<>();
                     ResultSet rs = ps.executeQuery();
                     while (rs.next()) {
                         String uuid = rs.getString("uuid");
                         String fullName = rs.getString("full_name");
                         resumes.add(new Resume(uuid.trim(), fullName.trim()));
                     }
                     return resumes;
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute(
                null,
                "SELECT COUNT(*) FROM resume",
                (parameter, ps) -> {
                    ResultSet rs = ps.executeQuery();
                    return rs.next() ? rs.getInt(1) : 0;
                }
        );
    }
}
