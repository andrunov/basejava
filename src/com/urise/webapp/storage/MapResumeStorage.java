package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {

    protected final Map<Resume, Resume> storage;

    public MapResumeStorage() {
        this.storage = new HashMap<>();
    }


    @Override
    public Resume doGet(Resume key) {
        return storage.get(key);
    }

    @Override
    public void doSave(Resume key, Resume resume) {
        storage.put(resume, resume);
    }

    @Override
    public void doUpdate(Resume key, Resume resume) {
        storage.put(resume, resume);
    }

    @Override
    public void doDelete(Resume key) {
        storage.remove(key);
    }

    @Override
    protected boolean isExist(Resume key) {
        return storage.containsKey(key);
    }

    @Override
    protected Resume searchKey(String uuid) {
        for (Resume resume : storage.keySet()){
            if (resume.getUuid().equals(uuid)) {
                return resume;
            }
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
        Resume key = searchKey(uuid);
        storage.remove(key);
    }

    @Override
    public int size() {
        return storage.size();
    }


}
