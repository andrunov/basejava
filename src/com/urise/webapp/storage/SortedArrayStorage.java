package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.printf("Element with ID=%s not present, updating impossible%n", r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        }
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            System.out.printf("Element with ID=%s is already present, please use update method%n", r.getUuid());
        } else {
            index = - index - 1;
            Resume[] partToBeShift = Arrays.copyOfRange(storage, index, size);
            System.arraycopy(partToBeShift, 0, storage, index + 1, size - index);
            storage[index] = r;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            Resume[] partToBeShift = Arrays.copyOfRange(storage, index + 1, size);
            System.arraycopy(partToBeShift, 0, storage, index, size - index - 1);
            size--;
        } else {
            System.out.printf("Element with ID=%s not present%n", uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        Resume[] result = new Resume[size];
        System.arraycopy(storage, 0, result, 0, size);
        return result;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
