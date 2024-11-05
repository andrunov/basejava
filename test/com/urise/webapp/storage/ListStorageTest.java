package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListStorageTest {

    protected static final String UUID_01 = "uuid1";
    protected static final String FIO_01 = "First Middle Last 1";

    protected static final String UUID_02 = "uuid2";
    protected static final String FIO_02 = "First Middle Last 2";

    protected static final String UUID_03 = "uuid3";
    protected static final String FIO_03 = "First Middle Last 3";

    protected static final String UUID_04 = "uuid4";
    protected static final String FIO_04 = "First Middle Last 4";

    protected static final String UUID_05 = "uuid5";
    protected static final String FIO_05 = "First Middle Last 5";

    protected static final Resume RESUME_01;
    protected static final Resume RESUME_02;
    protected static final Resume RESUME_03;
    protected static final Resume RESUME_04;
    protected static final Resume RESUME_05;

    static {
        RESUME_01 = new Resume(UUID_01, FIO_01);
        RESUME_02 = new Resume(UUID_02, FIO_02);
        RESUME_03 = new Resume(UUID_03, FIO_03);
        RESUME_04 = new Resume(UUID_04, FIO_04);
        RESUME_05 = new Resume(UUID_05, FIO_05);
    }

    protected final Storage storage;

    public ListStorageTest() {
        this.storage = new ListStorage();
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
        Assert.assertEquals(resume, storage.get(resume.getUuid(), resume.getFullName()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotFound() {
        storage.get(UUID_05, FIO_05);
    }

    @Test
    public void update() {
        Resume toBeUpdated = new Resume(UUID_02, FIO_02);
        storage.update(toBeUpdated);
        Assert.assertEquals(RESUME_02, storage.get(UUID_02, FIO_02));
    }


    @Test(expected = NotExistStorageException.class)
    public void updateNotFound() {
        storage.update(RESUME_05);
    }

    @Test
    public void getAll() {
        List<Resume> allResume = Arrays.asList(RESUME_01, RESUME_02, RESUME_03);
        Assert.assertEquals(allResume, storage.getAllSorted());

    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        List<Resume> allResume = new ArrayList<>();
        Assert.assertEquals(allResume, storage.getAllSorted());
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
        storage.delete(UUID_02, FIO_02);
        assertSize(2);
        storage.get(UUID_02, FIO_02);
    }
}
