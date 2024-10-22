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

    //template method
    public final void save( Resume resume ) {
        int index = getNotExistingSearchKey( resume.getUuid() );
        insertResume( index, resume );
    }

    public final void delete( String uuid ) {
        int index = getExistingSearchKey( uuid );
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
    public void insertResume(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    public void updateResume(Integer index, Resume resume) {
        storage.remove(((Integer)index).intValue());
        storage.add(resume);
    }

    @Override
    public Resume getResume(Integer index) {
        return storage.get( (Integer) index );
    }

    @Override
    public void removeResume(Integer index) {
        storage.remove(index.intValue());
    }

    @Override
    protected boolean isExist(Object key) {
        return (int) key != -1;
    }

}
