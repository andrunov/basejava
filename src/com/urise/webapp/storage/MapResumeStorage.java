package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {

    protected final Map<String, Resume> storage;

    public MapResumeStorage() {
        this.storage = new HashMap<>();
    }


    @Override
    public Resume doGet(Resume key) {
        return key;
    }

    @Override
    public void doSave(Resume key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void doUpdate(Resume key, Resume resume) {
        storage.put(key.getUuid(), resume);
    }

    @Override
    public void doDelete(Resume key) {
        storage.remove(key.getUuid());
    }

    @Override
    protected boolean isExist(Resume key) {
        return key != null;
    }

    @Override
    protected Resume searchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        return getAllSorted(new ArrayList<>(storage.values()));
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }


}
