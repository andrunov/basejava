package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> implements Storage{

    protected final List<Resume> storage;

    public ListStorage() {
        this.storage = new ArrayList<>();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public Integer getKeyForSave(Resume resume) {
        return getNotExistingSearchKey( resume.getUuid() );
    }

    @Override
    public void doSave(Integer key, Resume resume) {
        storage.add(resume);
    }

    public final void delete(String uuid ) {
        int index = getExistingSearchKey( uuid );
        doDelete(index);
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected Integer getSearchKey(String uuid ) {
        for (int i = 0; i < storage.size(); i++) {
            if ( storage.get(i).getUuid().equals(uuid) ) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void doUpdate(Integer key, Resume resume) {
        storage.remove(key.intValue());
        storage.add(resume);
    }

    @Override
    public Resume doGet(Integer key) {
        return storage.get(key);
    }

    @Override
    public void doDelete(Integer key) {
        storage.remove(key.intValue());
    }

    @Override
    protected boolean isExist(Integer key) {
        return key != -1;
    }

}
