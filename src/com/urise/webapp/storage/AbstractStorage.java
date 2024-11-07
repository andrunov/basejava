package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage <T> implements Storage {

    private static final Comparator<Resume> RESUME_UUID_COMPARATOR = (o1, o2) -> {
        int result = o1.getFullName().compareTo(o2.getFullName());
        if (result == 0) {
            result = o1.getUuid().compareTo(o2.getUuid());
        }
        return result;
    };

    public final Resume get(String uuid) {
        T key = getExistingSearchKey( uuid );
        return doGet( key );
    }

    public final void update( Resume resume ) {
        T key = getExistingSearchKey( resume.getUuid());
        doUpdate(key, resume);
    }

    public final void save(Resume resume) {
        T key = getNotExistingSearchKey( resume.getUuid());
        doSave(key, resume);
    }


    protected T getExistingSearchKey(String uuid) {
        T key = searchKey( uuid );
        if ( ! isExist(key) ) {
            throw new NotExistStorageException( uuid );
        } else {
            return key;
        }
    }

    protected T getNotExistingSearchKey(String uuid ) {
        T key = searchKey( uuid );
        if ( isExist(key)) {
            throw new ExistStorageException( uuid );
        } else {
            return key;
        }
    }

    public List<Resume> getAllSorted(List<Resume> storage) {
        storage.sort(RESUME_UUID_COMPARATOR);
        return storage;
    }

    public abstract Resume doGet(T key );

    public abstract void doSave( T key, Resume resume );

    public abstract void doUpdate(T key, Resume resume );

    public abstract void doDelete(T key );

    protected abstract boolean isExist(T key);

    protected abstract T searchKey(String uuid );



}
