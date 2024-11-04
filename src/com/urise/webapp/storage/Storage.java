package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public interface Storage {

    void clear();

    <T> void update(Resume resume );

    void save(Resume resume);

    <T> Resume get(String uuid, String fullName);

    void delete(String uuid, String fullName);

    Resume[] getAll();

    int size();
}
