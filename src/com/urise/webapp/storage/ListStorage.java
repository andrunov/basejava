package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage implements Storage{

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

    protected Integer searchKey(String uuid ) {
        for (int i = 0; i < storage.size(); i++) {
            if ( storage.get(i).getUuid().equals(uuid) ) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void insertResume(Object index, Resume resume) {
        storage.add(resume);
    }

    @Override
    public void updateResume(Object index, Resume resume) {
        storage.remove(index);
        storage.add(resume);
    }

    @Override
    public Resume getResume(Object index) {
        return storage.get((int)index);
    }

    @Override
    public void removeResume(Object index) {
        storage.remove( (int)index );
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key != -1;
    }

}
