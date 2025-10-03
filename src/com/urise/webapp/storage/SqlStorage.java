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
                    addContacts(resume);
                    addSections(resume);
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
                    Map<String, Map<ContactType, String>> contactMap = getAllContacts();
                    Map<String, Map<SectionType, Section<?>>> sectionMap = getAllSections();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Resume resume = Resume.of(rs);
                        for (Map.Entry<ContactType, String> contactEntry : contactMap.get(resume.getUuid()).entrySet()) {
                            resume.addContact(contactEntry.getKey(), contactEntry.getValue());
                        }
                        for (Map.Entry<SectionType, Section<?>> sectionEntry : sectionMap.get(resume.getUuid()).entrySet()) {
                            resume.addSection(sectionEntry.getKey(), sectionEntry.getValue());
                        }
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
            ps.execute();
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

    public void addContacts(Resume resume) {
        sqlHelper.execute(
                "SELECT * FROM contact c WHERE c.resume_uuid =? ", ps -> {
                    ps.setString(1, resume.getUuid());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resume.addContactOf(rs);
                    }
                    return null;
                });
    }

    public Map<String, Map<ContactType, String>> getAllContacts() {
        return sqlHelper.execute(
                "SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid ", ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Map<ContactType, String>> result = new HashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid").trim();
                        if (!result.containsKey(uuid)) {
                            result.put(uuid, new HashMap<>());
                        }
                        String value = rs.getString("value");
                        String str_type = rs.getString("type");
                        if (str_type != null && value != null) {
                            ContactType type = ContactType.valueOf(str_type);

                            result.get(uuid).put(type, value);
                        }
                    }
                    return result;
                });
    }

    public Map<String, Map<SectionType, Section<?>>> getAllSections() {
        return sqlHelper.execute(
                "SELECT * FROM resume r LEFT JOIN section s ON r.uuid = s.resume_uuid ", ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Map<SectionType, Section<?>>> result = new HashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid").trim();
                        if (!result.containsKey(uuid)) {
                            result.put(uuid, new HashMap<>());
                        }
                        String value = rs.getString("value");
                        String str_type = rs.getString("type");
                        if (str_type != null && value != null) {
                            SectionType type = SectionType.valueOf(str_type);
                            result.get(uuid).put(type, Section.of(type, value));
                        }
                    }
                    return result;
                });
    }

    private void deleteSections(Connection conn, String uuid) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid = ?")) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }

    private void insertSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section<?>> e : resume.getSections().entrySet()) {

                SectionType key = e.getKey();
                Section<?> value = e.getValue();

                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());

                switch (key) {
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(3, String.valueOf(value.getValue()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                    case EDUCATION:
                        List<String> listSection = (List<String>) value.getValue();
                        StringBuilder builder = new StringBuilder();
                        for (String val : listSection) {
                            builder.append(val);
                            builder.append("\n");
                        }
                        ps.setString(3, builder.toString());
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void addSections(Resume resume) {
        sqlHelper.execute(
                "SELECT * FROM section c WHERE c.resume_uuid =? ", ps -> {
                    ps.setString(1, resume.getUuid());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resume.addSectionOf(rs);
                    }
                    return null;
                });
    }
}
