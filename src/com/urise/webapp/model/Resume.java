package com.urise.webapp.model;

import com.urise.webapp.util.JsonParser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Initial resume class
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {

    public static Resume of(ResultSet rs) throws SQLException {
        String uuid = rs.getString("uuid");
        String fullName = rs.getString("full_name");
        return new Resume(uuid.trim(), fullName.trim());
    }

    private static final long serialVersionUID = 1L;

    private String uuid;

    private String fullName;

    private Map<SectionType, Section<?>> sections;

    private Map<ContactType, String> contacts;

    public Resume() {
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.sections = new EnumMap<>(SectionType.class);
        this.contacts = new EnumMap<>(ContactType.class);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setContact(ContactType key, String value) {
        contacts.put(key, value);
    }

    public String getContact(ContactType key) {
        return contacts.get(key);
    }

    public void setSection(SectionType key, Section<?> value) {
        sections.put(key, value);
    }

    public Section<?> getSection(SectionType key) {
        return sections.get(key);
    }

    public Map<SectionType, Section<?>> getSections() {
        return sections;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(fullName);
        sb.append("\n\n");
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            sb.append(String.format("%-23s :    %-30.30s",entry.getKey().getTitle(), entry.getValue()));
            sb.append("\n");
        }
        sb.append("\n");
        for (Map.Entry<SectionType, Section<?>> entry : sections.entrySet()) {
            sb.append(entry.getKey().getTitle());
            sb.append("\n");
            sb.append(entry.getValue().toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public void addContact(ContactType key, String value) {
        contacts.put(key, value);
    }

    public void addSection(SectionType key, Section<?> value) {
        sections.put(key, value);
    }

    public void addContactOf(ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        String str_type = rs.getString("type");
        if (str_type != null && value != null) {
            ContactType type = ContactType.valueOf(str_type);
            this.addContact(type, value);
        }
    }

    public void addSectionOf(ResultSet rs) throws SQLException {
        String content = rs.getString("value");
        if (content != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            this.addSection(type, JsonParser.read(content, Section.class));
        }
    }

}
