package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> implements Storage {

    private Map<String, Resume> map = new HashMap<>();


    @Override
    public Resume doGet(String key) {
        return null;
    }

    @Override
    public void doSave(String key, Resume resume) {

    }

    @Override
    public void doUpdate(String key, Resume resume) {

    }

    @Override
    public void doDelete(String key) {

    }

    @Override
    protected boolean isExist(String key) {
        return false;
    }

    @Override
    protected String searchKey(String uuid) {
        return null;
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public void clear() {

    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public int size() {
        return 0;
    }
}
