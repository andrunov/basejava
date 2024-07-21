package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array storage for Resumes with auto sorting
 */
public class SortedArrayStorage extends AbstractArrayStorage{

    public SortedArrayStorage() {
        super();
    }

    //for new resume only
    @Override
    public void insertResume(int index, Resume r) {
        int insertionIndex = -index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
        storage[insertionIndex] = r;
    }


    @Override
    public void removeResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }


    @Override
    protected Integer searchKey(String uuid) {
        Resume searchElement = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchElement);
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key >= 0;
    }
}
