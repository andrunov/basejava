package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage <T> implements Storage {

    public final Resume get(String uuid, String fullName ) {
        T key = getExistingSearchKey( uuid, fullName );
        return doGet( key );
    }

    public final void update( Resume resume ) {
        T key = getExistingSearchKey( resume.getUuid(), resume.getFullName() );
        doUpdate(key, resume);
    }

    public final void save(Resume resume) {
        T key = getNotExistingSearchKey( resume.getUuid(), resume.getFullName() );
        doSave(key, resume);
    }


    protected T getExistingSearchKey(String uuid, String fullName) {
        T key = searchKey( uuid, fullName );
        if ( ! isExist(key) ) {
            throw new NotExistStorageException( uuid );
        } else {
            return key;
        }
    }

    protected T getNotExistingSearchKey(String uuid, String fullName ) {
        T key = searchKey( uuid, fullName );
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

    protected abstract boolean isExist(T key);

    protected abstract T searchKey(String uuid, String fullName );



}
