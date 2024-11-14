package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends  AbstractStorage<String> {

    protected final Map<String, Resume> storage;

    public MapUuidStorage() {
        this.storage = new HashMap<>();
    }

    @Override
    public void doSave(String key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected String searchKey(String uuid) {
        return uuid;
    }

    @Override
    public void doUpdate(String key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume doGet(String key) {
        return storage.get(key);
    }

    @Override
    public void doDelete(String key) {
        storage.remove(key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        return getAllSorted(new ArrayList<>(storage.values()));
    }

    @Override
    public int size() {
        return storage.size();
    }
}
