package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Initial resume class
 */
public class Resume  {

    // Unique identifier
    private String uuid;

    private String fullName;

    private final Map<SectionType, Section<?> > sections;

    private final Map<ContactType, String> contacts;

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.sections = new EnumMap<SectionType, Section<?>>(SectionType.class);
        this.contacts = new EnumMap<ContactType, String>(ContactType.class);
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

}
