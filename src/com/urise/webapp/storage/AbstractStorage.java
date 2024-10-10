package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage {

    public final <T> Resume get(String uuid ) {
        T index = getExistingSearchKey( uuid );
        return getResume( index );
    }

    public final <T> void update( Resume resume ) {
        T index = getExistingSearchKey( resume.getUuid() );
        updateResume(index, resume);
    }

    protected <T> T getExistingSearchKey(String uuid) {
        T searchKey = searchKey( uuid );
        if ( ! isExist(searchKey) ) {
            throw new NotExistStorageException( uuid );
        } else {
            return searchKey;
        }
    }

    protected <T> T getNotExistingSearchKey(String uuid ) {
        T searchKey = searchKey( uuid );
        if ( isExist(searchKey)) {
            throw new ExistStorageException( uuid );
        } else {
            return searchKey;
        }
    }

    protected abstract boolean isExist(Object key);

    protected abstract <T> T searchKey(String uuid );

    public abstract <T> void insertResume( T index, Resume resume );

    public abstract <T> void updateResume( T index, Resume resume );

    public abstract <T> Resume getResume(T index );

    public abstract <T> void removeResume(T index );

}
