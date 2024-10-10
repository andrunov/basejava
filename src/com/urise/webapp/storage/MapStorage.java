package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends  AbstractStorage implements Storage {

    protected final Map<String, Resume> storage;

    public MapStorage() {
        this.storage = new HashMap<>();
    }


    @Override
    protected boolean isExist(Object key) {
        return key != null;
    }

    @Override
    protected String searchKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        } else {
            return null;
        }
    }

    @Override
    public <T> void insertResume(T index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public <T> void updateResume(T index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public <T> Resume getResume(T index) {
        return storage.get(index);
    }

    @Override
    public <T> void removeResume(T index) {
        storage.remove(index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Resume resume) {
        String index = getNotExistingSearchKey( resume.getUuid() );
        insertResume( index, resume );
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
