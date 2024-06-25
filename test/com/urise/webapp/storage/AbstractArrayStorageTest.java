package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {

    protected static final String UUID_01 = "uuid1";
    protected static final String UUID_02 = "uuid2";
    protected static final String UUID_03 = "uuid3";
    protected static final String UUID_04 = "uuid4";
    protected static final String UUID_05 = "uuid5";
    protected static Resume RESUME_01 = new Resume(UUID_01);
    protected static Resume RESUME_02 = new Resume(UUID_02);
    protected static Resume RESUME_03 = new Resume(UUID_03);
    protected static Resume RESUME_04 = new Resume(UUID_04);
    protected static Resume RESUME_05 = new Resume(UUID_05);


    protected Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
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
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Assert.assertEquals(RESUME_01, storage.get(UUID_01));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotFound() {
        storage.get(UUID_05);
    }

    @Test
    public void update() {
        String updated_uuid = "updated_uuid";
        RESUME_02.setUuid(updated_uuid);
        storage.update(RESUME_02);
        Assert.assertEquals(RESUME_02, storage.get(updated_uuid));
    }



    @Test
    public void update_NOT_WORKS_FOR_SORTES_ARRAY_STORAGE() {
        String updated_uuid = "updated_uuid";
        RESUME_03.setUuid(updated_uuid);
        storage.update(RESUME_03);
        Assert.assertEquals(RESUME_03, storage.get(updated_uuid));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotFound() {
        storage.update(RESUME_05);
    }

    @Test
    public void getAll() {
        Resume[] all_resume = new Resume[3];
        all_resume[0] = RESUME_01;
        all_resume[1] = RESUME_02;
        all_resume[2] = RESUME_03;
        Assert.assertArrayEquals(all_resume, storage.getAll());

    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(RESUME_04);
        Assert.assertEquals(RESUME_04, storage.get(UUID_04));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_01);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        int i = storage.size() + 1;
        try {
            while (i <= AbstractArrayStorage.STORAGE_LIMIT) {
                Resume resume = new Resume(String.format("uuid%s", i));
                storage.save(resume);
                i++;
            }
        } catch (StorageException e) {
            Assert.fail("overflow events prematurely");
        }
        Resume resume = new Resume(String.format("uuid%s", i));
        storage.save(resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_02);
        storage.get(UUID_02);
    }
}