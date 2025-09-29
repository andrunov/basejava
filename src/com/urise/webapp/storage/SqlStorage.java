package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {


    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(
                "SELECT * FROM resume r WHERE r.uuid =? ", ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    extractAndSetContacts(resume);
                    extractAndSetSections(resume);
                    return resume;
                });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new NotExistStorageException("Resume " + resume.getUuid() + " not found");
                }
            }
            deleteContacts(conn, resume.getUuid());
            deleteSections(conn, resume.getUuid());
            insertContacts(conn, resume);
            insertSections(conn, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(conn, resume);
            insertSections(conn, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
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
                "SELECT * FROM resume r ORDER BY full_name, uuid", ps -> {
                    List<Resume> result = new ArrayList<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Resume resume = Resume.of(rs);
                        extractAndSetContacts(resume);
                        extractAndSetSections(resume);
                        result.add(resume);
                    }
                    return result;
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void deleteContacts(Connection conn, String uuid) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
            ps.setString(1, uuid);
            ps.executeUpdate();
        }
    }

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void extractAndSetContacts(Resume resume) {
        sqlHelper.execute(
                "SELECT * FROM contact c WHERE c.resume_uuid =? ", ps -> {
                    Map<ContactType, String> result = new HashMap<>();
                    ps.setString(1, resume.getUuid());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resume.addContactOf(rs);
                    }
                    return null;
                });
    }

    private void deleteSections(Connection conn, String uuid) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid = ?")) {
            ps.setString(1, uuid);
            ps.executeUpdate();
        }
    }

    private void insertSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section<?>> e : resume.getSections().entrySet()) {

                SectionType key = e.getKey();
                Section<?> value = e.getValue();

                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());

                if (!SectionType.EXPERIENCE.equals(key)) {
                    //TEXT section
                    if (SectionType.OBJECTIVE.equals(key) || SectionType.PERSONAL.equals(key)) {
                        ps.setString(3, String.valueOf(value.getValue()));

                    //LIST section
                    } else {
                        List<String> listSection = (List<String>) value.getValue();
                        StringBuilder builder = new StringBuilder();
                        for (String val : listSection) {
                            builder.append(val);
                            builder.append("\n");
                        }
                        ps.setString(3, builder.toString());
                    }
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void extractAndSetSections(Resume resume) {
        sqlHelper.execute(
                "SELECT * FROM section c WHERE c.resume_uuid =? ", ps -> {
                    Map<SectionType, Section> result = new HashMap<>();
                    ps.setString(1, resume.getUuid());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resume.addSectionOf(rs);
                    }
                    return null;
                });
    }
}
