package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;

public abstract class AbstractStorage {

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
}
