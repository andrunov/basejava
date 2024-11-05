package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public interface Storage {

    static final Comparator<Resume> RESUME_UUID_COMPARATOR = (o1, o2) -> {
        int result = o1.getFullName().compareTo(o2.getFullName());
        if (result == 0) {
            result = o1.getUuid().compareTo(o2.getUuid());
        }
        return result;
    };

    void clear();

    <T> void update(Resume resume );

    void save(Resume resume);

    <T> Resume get(String uuid, String fullName);

    void delete(String uuid, String fullName);

    List<Resume> getAllSorted();

    int size();
}
