package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage{
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.printf("Resume %s not exist", uuid);
            return null;
        }
        return storage[index];
    }

    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.printf("Element with ID=%s not present, updating impossible%n", resume.getUuid());
        }
    }

    @Override
    public final Resume[] getAll() {
        Resume[] result = new Resume[size];
        System.arraycopy(storage, 0, result, 0, size);
        return result;
    }

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    //template method
    public final void save(Resume r) {
        if (checkIsFull()) return;
        if (checkIsPresent(r)) return;
        insert(r);
    }

    protected boolean checkIsFull() {
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage is full");
            return true;
        }
        return false;
    }

    protected  boolean checkIsPresent(Resume r) {
        if (getIndex(r.getUuid()) >= 0) {
            System.out.printf("Element with ID=%s is already present, please use update method%n", r.getUuid());
            return true;
        }
        return false;
    }

    //template method
    public final void insert(Resume resume) {
        add(resume);
        size++;
    }

    protected abstract void add(Resume resume);

    //template method
    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            delete(index);
        } else {
            System.out.printf("Element with ID=%s not present%n", uuid);
        }
    }

    //template method
    public final void delete(int index) {
        remove(index);
        size--;
    }

    protected abstract void remove(int index);


    protected abstract int getIndex(String uuid);
}
