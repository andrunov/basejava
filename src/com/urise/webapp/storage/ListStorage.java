package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage implements Storage{

    protected List<Resume> storage;

    public ListStorage() {
        this.storage = new ArrayList<>();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save( Resume resume ) {
        int index = getIndex( resume.getUuid() );
        if ( index >= 0 ) {
            throw new ExistStorageException( resume.getUuid() );
        } else {
            insertResume(index, resume);
        }
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected int getIndex( String uuid ) {
        for (int i = 0; i < storage.size(); i++) {
            if ( storage.get(i).getUuid().equals(uuid) ) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void insertResume(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    public void updateResume(int index, Resume resume) {
        storage.remove(index);
        storage.add(resume);
    }

    @Override
    public Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    public void removeResume(int index) {
        storage.remove( index );
    }

    @Override
    public void decreaseStorage() {
        //do nothing
    }
}
