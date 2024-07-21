package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage {

    public final Resume get(String uuid ) {
        int index = getExistingSearchKey( uuid );
        return getResume( index );
    }

    public final void update( Resume resume ) {
        int index = getExistingSearchKey( resume.getUuid() );
        updateResume(index, resume);
    }

    protected int getExistingSearchKey(String uuid) {
        int searchKey = (Integer) searchKey( uuid );
        if ( ! isExist(searchKey) ) {
            throw new NotExistStorageException( uuid );
        } else {
            return searchKey;
        }
    }

    protected int getNotExistingSearchKey(String uuid ) {
        int searchKey = (Integer) searchKey( uuid );
        if ( isExist(searchKey)) {
            throw new ExistStorageException( uuid );
        } else {
            return searchKey;
        }
    }

    protected abstract boolean isExist(Object key);

    protected abstract Object searchKey(String uuid );

    public abstract void insertResume( int index, Resume resume );

    public abstract void updateResume( int index, Resume resume );

    public abstract Resume getResume(int index);

    public abstract void removeResume(int index );

}
