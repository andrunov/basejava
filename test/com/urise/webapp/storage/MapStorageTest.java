package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class MapStorageTest {

    protected static final String UUID_01 = "uuid1";
    protected static final String UUID_02 = "uuid2";
    protected static final String UUID_03 = "uuid3";
    protected static final String UUID_04 = "uuid4";
    protected static final String UUID_05 = "uuid5";
    protected static final Resume RESUME_01;
    protected static final Resume RESUME_02;
    protected static final Resume RESUME_03;
    protected static final Resume RESUME_04;
    protected static final Resume RESUME_05;

    static {
        RESUME_01 = new Resume(UUID_01);
        RESUME_02 = new Resume(UUID_02);
        RESUME_03 = new Resume(UUID_03);
        RESUME_04 = new Resume(UUID_04);
        RESUME_05 = new Resume(UUID_05);
    }

    protected final Storage storage;

    public MapStorageTest() {
        this.storage = new MapStorage();
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_01);
        storage.save(RESUME_02);
        storage.save(RESUME_03);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    @Test
    public void get() {
        assertGet(RESUME_01);
        assertGet(RESUME_02);
        assertGet(RESUME_03);
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotFound() {
        storage.get(UUID_05);
    }

    @Test
    public void update() {
        Resume toBeUpdated = new Resume(UUID_02);
        storage.update(toBeUpdated);
        Assert.assertEquals(RESUME_02, storage.get(UUID_02));
    }


    @Test(expected = NotExistStorageException.class)
    public void updateNotFound() {
        storage.update(RESUME_05);
    }

    @Test
    public void getAll() {
        Resume[] allResume = new Resume[]{RESUME_01, RESUME_02, RESUME_03};
        Resume[] sortedResult = Arrays.copyOf(storage.getAll(), storage.size());
        Arrays.sort(sortedResult, (o1, o2) -> o1.getUuid().compareTo(o2.getUuid()));
        Assert.assertArrayEquals(allResume, sortedResult);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        Resume[] allResume = new Resume[0];
        Assert.assertArrayEquals(allResume, storage.getAll());
    }

    @Test
    public void save() {
        storage.save(RESUME_04);
        assertSize(4);
        assertGet(RESUME_04);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_01);
    }

    @Test()
    public void saveOverflow() {
       //do nothing
    }


    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_02);
        assertSize(2);
        storage.get(UUID_02);
    }
}
