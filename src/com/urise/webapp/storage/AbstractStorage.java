package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage <T> {

    public final Resume get(String uuid ) {
        T key = getExistingSearchKey( uuid );
        return doGet( key );
    }

    public final void update( Resume resume ) {
        T key = getExistingSearchKey( resume.getUuid() );
        doUpdate(key, resume);
    }

    public final void save(Resume resume) {
        T key = getKeyForSave( resume );
        doSave(key, resume);
    }


    protected T getExistingSearchKey(String uuid) {
        T key = getSearchKey( uuid );
        if ( ! isExist(key) ) {
            throw new NotExistStorageException( uuid );
        } else {
            return key;
        }
    }

    protected T getNotExistingSearchKey(String uuid ) {
        T key = getSearchKey( uuid );
        if ( isExist(key)) {
            throw new ExistStorageException( uuid );
        } else {
            return key;
        }
    }

    public abstract Resume doGet(T key );

    public abstract void doSave( T key, Resume resume );

    public abstract void doUpdate(T key, Resume resume );

    public abstract void doDelete(T key );

    public abstract T getKeyForSave(Resume resume );

    protected abstract boolean isExist(T key);

    protected abstract T getSearchKey(String uuid );



}
