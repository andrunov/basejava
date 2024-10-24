package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends  AbstractStorage<String> implements Storage {

    protected final Map<String, Resume> storage;

    public MapStorage() {
        this.storage = new HashMap<>();
    }

    @Override
    public String getKeyForSave(Resume resume) {
        return getNotExistingSearchKey( resume.getUuid() );
    }

    @Override
    public void doSave(String index, Resume resume) {
        insertResume( index, resume );
    }

    @Override
    protected boolean isExist(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public void insertResume(String index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void updateResume(String index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume getResume(String index) {
        return storage.get(index);
    }

    @Override
    public void removeResume(String index) {
        storage.remove(index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void delete(String uuid) {
        storage.remove(uuid);
    }

    @Override
    public Resume[] getAll() {
        Resume[] result = new Resume[storage.size()];
        int counter = 0;
        for (Resume resume : storage.values()) {
            result[counter] = resume;
            counter++;
        }
        return result;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
