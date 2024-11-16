package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage <T> implements Storage {

    private static final Comparator<Resume> RESUME_UUID_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public final Resume get(String uuid) {
        LOG.info("Get " + uuid);
        T key = getExistingSearchKey( uuid );
        return doGet( key );
    }

    public final void update( Resume resume ) {
        LOG.info("Update " + resume);
        T key = getExistingSearchKey( resume.getUuid());
        doUpdate(key, resume);
    }

    public final void save(Resume resume) {
        LOG.info("Save " + resume);
        T key = getNotExistingSearchKey( resume.getUuid());
        doSave(key, resume);
    }

    public final void delete(String uuid ) {
        LOG.info("Delete " + uuid);
        T key = getExistingSearchKey( uuid );
        doDelete(key);
    }


    protected T getExistingSearchKey(String uuid) {
        T key = searchKey( uuid );
        if ( ! isExist(key) ) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException( uuid );
        } else {
            return key;
        }
    }

    protected T getNotExistingSearchKey(String uuid ) {
        T key = searchKey( uuid );
        if ( isExist(key)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException( uuid );
        } else {
            return key;
        }
    }

    public List<Resume> getAllSorted(List<Resume> storage) {
        LOG.info("GetAllSorted");
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
