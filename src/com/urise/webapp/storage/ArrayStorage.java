package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {


    public ArrayStorage() {
        super();
    }

    @Override
    public void insertResume(int index, Resume r) {
        storage[size] = r;
    }


    @Override
    public void removeResume(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
    }

    /*
    return index in array storage if element is present, else return -1
     */
    protected Integer searchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key != -1;
    }

}
