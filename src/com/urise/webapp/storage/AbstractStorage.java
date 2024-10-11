package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage {

    public final Resume get(String uuid ) {
        Object index = getExistingSearchKey( uuid );
        return getResume( index );
    }

    public final void update( Resume resume ) {
        Object index = getExistingSearchKey( resume.getUuid() );
        updateResume(index, resume);
    }

    protected Object getExistingSearchKey(String uuid) {
        Object searchKey = searchKey( uuid );
        if ( ! isExist(searchKey) ) {
            throw new NotExistStorageException( uuid );
        } else {
            return searchKey;
        }
    }

    protected Object getNotExistingSearchKey(String uuid ) {
        Object searchKey = searchKey( uuid );
        if ( isExist(searchKey)) {
            throw new ExistStorageException( uuid );
        } else {
            return searchKey;
        }
    }

    protected abstract boolean isExist(Object key);

    protected abstract Object searchKey(String uuid );

    public abstract void insertResume( Object index, Resume resume );

    public abstract void updateResume( Object index, Resume resume );

    public abstract Resume getResume(Object index);

    public abstract void removeResume(Object index );

}
