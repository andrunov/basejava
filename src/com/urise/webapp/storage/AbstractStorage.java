package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage {

    public final Resume get(String uuid ) {
        int index = getExisted( uuid );
        return getResume( index );
    }

    //template method
    public final void save( Resume resume ) {
        checkOverflow(resume.getUuid());
        int index = getNotExisted( resume.getUuid() );
        insertResume( index, resume );
        increaseStorage();
    }

    public final void update( Resume resume ) {
        int index = getExisted( resume.getUuid() );
        updateResume(index, resume);
    }

    public final void delete( String uuid ) {
        int index = getExisted( uuid );
        removeResume(index);
        decreaseStorage();
    }

    private int getExisted( String uuid) {
        int index = getIndex( uuid );
        if ( index < 0 ) {
            throw new NotExistStorageException( uuid );
        } else {
            return index;
        }
    }

    private int getNotExisted( String uuid ) {
        int index = getIndex( uuid );
        if ( index >= 0 ) {
            throw new ExistStorageException( uuid );
        } else {
            return index;
        }
    }

    protected abstract int getIndex( String uuid );

    public abstract void insertResume( int index, Resume resume );

    public abstract void updateResume( int index, Resume resume );

    public abstract Resume getResume(int index);

    public abstract void removeResume(int index );

    public abstract void increaseStorage();

    public abstract void decreaseStorage();

    public abstract void checkOverflow(String uuid);

}
