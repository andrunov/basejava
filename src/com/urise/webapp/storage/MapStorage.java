package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage implements Storage{

    protected final Map<String, Resume> storage;

    public MapStorage() {
        this.storage = new HashMap<>();
    }

    @Override
    public void clear() {
        storage.clear();
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

    //template method
    public final void save( Resume resume ) {
        Object index = getNotExistingSearchKey( resume.getUuid() );
        insertResume( index, resume );
    }

    public final void delete( String uuid ) {
        Object index = getExistingSearchKey( uuid );
        removeResume(index);
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected String searchKey(String uuid ) {
        if (storage.containsKey(uuid)) {
            return uuid;
        } else {
            return null;
        }
    }

    @Override
    public void insertResume(Object index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void updateResume(Object index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume getResume(Object index) {
        return storage.get(index);
    }

    @Override
    public void removeResume(Object index) {
        storage.remove( index );
    }

    @Override
    protected boolean isExist(Object key) {
        return key != null;
    }

}
