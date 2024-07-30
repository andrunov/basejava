package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage implements Storage{

    protected final Map<String, Resume> storage;

    public MapStorage() {
        storage = new HashMap<>();
    }

    @Override
    protected boolean isExist(Object key) {
        return false;
    }

    @Override
    protected Object searchKey(String uuid) {
        return storage;
    }

    public void insertResume(int index, Resume resume) {

    }

    public void updateResume(int index, Resume resume) {

    }

    public Resume getResume(int index) {
        return null;
    }

    public void removeResume(int index) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void update(Resume resume) {

    }

    @Override
    public void save(Resume resume) {

    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }
}
