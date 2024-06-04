package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array storage for Resumes with auto sorting
 */
public class SortedArrayStorage extends AbstractArrayStorage{

    public SortedArrayStorage() {
        storage = new Resume[STORAGE_LIMIT];
    }

    @Override
    public void add(Resume r) {
        int index = getIndex(r.getUuid());
        index = - index - 1;
        Resume[] partToBeShift = Arrays.copyOfRange(storage, index, size);
        System.arraycopy(partToBeShift, 0, storage, index + 1, size - index);
        storage[index] = r;
    }


    @Override
    public void remove(int index) {
        Resume[] partToBeShift = Arrays.copyOfRange(storage, index + 1, size);
        System.arraycopy(partToBeShift, 0, storage, index, size - index - 1);
    }


    @Override
    protected int getIndex(String uuid) {
        Resume searchElement = new Resume();
        searchElement.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchElement);
    }
}
