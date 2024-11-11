package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<String> {

    protected final Map<String, Resume> storage;

    public MapResumeStorage() {
        this.storage = new HashMap<>();
    }


    @Override
    public Resume doGet(String key) {
        return storage.get(key);
    }

    @Override
    public void doSave(String key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void doUpdate(String key, Resume resume) {
        storage.put(key, resume);
    }

    @Override
    public void doDelete(String key) {
        storage.remove(key);
    }

    @Override
    protected boolean isExist(String key) {
        return key != null;
    }

    @Override
    protected String searchKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return storage.get(uuid).getUuid();
        }
        return null;
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void delete(String uuid) {
        String key = searchKey(uuid);
        storage.remove(key);
    }

    @Override
    public int size() {
        return storage.size();
    }


}
