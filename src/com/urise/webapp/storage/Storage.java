package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public interface Storage {


    void clear();

    <T> void update(Resume resume );

    void save(Resume resume);

    <T> Resume get(String uuid, String fullName);

    void delete(String uuid, String fullName);

    List<Resume> getAll();

    int size();
}
