package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage <T> {

    public final Resume get(String uuid ) {
        T index = getExistingSearchKey( uuid );
        return getResume( index );
    }

    public final void update( Resume resume ) {
        T index = getExistingSearchKey( resume.getUuid() );
        updateResume(index, resume);
    }

    protected T getExistingSearchKey(String uuid) {
        T searchKey = getSearchKey( uuid );
        if ( ! isExist(searchKey) ) {
            throw new NotExistStorageException( uuid );
        } else {
            return searchKey;
        }
    }

    protected T getNotExistingSearchKey(String uuid ) {
        T searchKey = getSearchKey( uuid );
        if ( isExist(searchKey)) {
            throw new ExistStorageException( uuid );
        } else {
            return searchKey;
        }
    }

    public void save(Resume resume) {
        T index = getNotExistingSearchKey( resume.getUuid() );
        insertResume( index, resume );
    }

    protected abstract boolean isExist(T key);

    protected abstract T getSearchKey(String uuid );

    public abstract void insertResume( T index, Resume resume );

    public abstract void updateResume( T index, Resume resume );

    public abstract Resume getResume(T index );

    public abstract void removeResume(T index );

}
