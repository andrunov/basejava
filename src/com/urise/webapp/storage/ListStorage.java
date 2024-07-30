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
    public void update(Resume resume) {
        int index = getExistingSearchKey( resume.getUuid() );
        storage.set(index, resume);
   }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    public final void save( Resume resume ) {
        int index = getNotExistingSearchKey( resume.getUuid() );
        insertResume( index, resume );
    }

    @Override
    public Resume get(String uuid) {
        int index = getExistingSearchKey( uuid );
        return storage.get(index);
    }

    public final void delete( String uuid ) {
        int index = getExistingSearchKey( uuid );
        storage.remove(index);
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

    public void insertResume(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key != -1;
    }

}
