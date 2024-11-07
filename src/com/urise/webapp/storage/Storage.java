package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

public interface Storage {


    void clear();

    <T> void update(Resume resume );

    void save(Resume resume);

    <T> Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAll();

    int size();
}
