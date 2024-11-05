package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {

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
    protected String searchKey(String uuid, String fullName) {
        return null;
    }

    @Override
    public List<Resume> getAllSorted() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void delete(String uuid, String fullName) {

    }

    @Override
    public int size() {
        return 0;
    }


}
