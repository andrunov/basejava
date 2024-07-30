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
        return key != null;
    }

    @Override
    protected Object searchKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume) {
        int index = getExistingSearchKey( resume.getUuid() );
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void save(Resume resume) {
        int index = getNotExistingSearchKey( resume.getUuid() );
        storage.put( resume.getUuid() , resume );
    }

    @Override
    public Resume get(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void delete(String uuid) {
        storage.remove(uuid);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
