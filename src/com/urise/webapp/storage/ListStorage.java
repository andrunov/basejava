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
    public <T> void insertResume(T index, Resume resume) {
        storage.add(resume);
    }

    @Override
    public <T> void updateResume(T index, Resume resume) {
        storage.remove(((Integer)index).intValue());
        storage.add(resume);
    }

    @Override
    public <T> Resume getResume(T index) {
        return storage.get( (Integer) index );
    }

    @Override
    public <T> void removeResume(T index) {
        storage.remove(((Integer)index).intValue());
    }

    @Override
    protected boolean isExist(Object key) {
        return (int) key != -1;
    }

}
