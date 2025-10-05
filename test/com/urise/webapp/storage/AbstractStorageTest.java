package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.storage.TestData.*;

public abstract class AbstractStorageTest {

    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
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
    public void get() {
        assertGet(RESUME_01);
        assertGet(RESUME_02);
        assertGet(RESUME_03);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    protected void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    protected void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotFound() {
        storage.get(UUID_05);
    }

    @Test
    public void update() {
        Resume toBeUpdated = new Resume(UUID_02, FIO_02);
        toBeUpdated.setContact(ContactType.PHONE, "+7(111) 111-1111");
        storage.update(toBeUpdated);
        Assert.assertEquals(RESUME_02, storage.get(UUID_02));
        Assert.assertTrue(storage.get(UUID_02).getContact(ContactType.PHONE).equals("+7(111) 111-1111"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotFound() {
        storage.update(RESUME_05);
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

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        if (storage instanceof ArrayStorage || storage instanceof SortedArrayStorage) {
            storage.clear();
            try {
                for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                    Resume resume = new Resume(String.format("uuid%s", i), String.format("FIO%s", i));
                    storage.save(resume);
                }
            } catch (StorageException e) {
                Assert.fail("overflow events prematurely");
            }
            Resume resume = new Resume(String.format("uuid%s", "overflowing_resume"), "SomeFIO");
            storage.save(resume);
        }
    }


    @Test
    public void getAll() {
        List<Resume> allResume = Arrays.asList(RESUME_01, RESUME_02, RESUME_03);
        //Assert.assertEquals(allResume, storage.getAllSorted());
        Assert.assertEquals(RESUME_01.getContacts().size(), storage.getAllSorted().get(0).getContacts().size());


    }


    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        List<Resume> allResume = new ArrayList<>();
        Assert.assertEquals(allResume, storage.getAllSorted());
    }


    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_02);
        assertSize(2);
        storage.get(UUID_02);
    }

}
