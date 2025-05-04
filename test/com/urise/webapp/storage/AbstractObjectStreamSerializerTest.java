package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.ObjectStreamSerializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.storage.TestData.*;

public abstract class AbstractObjectStreamSerializerTest {

    protected ObjectStreamSerializer objectStreamSerializer;

    public AbstractObjectStreamSerializerTest() {
        this.objectStreamSerializer = new ObjectStreamSerializer();
    }

    @Before
    public abstract void setUp();

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
        Assert.assertEquals(size, objectStreamSerializer.getStorage().size());
    }

    protected void assertGet(Resume resume) {
        Assert.assertEquals(resume, objectStreamSerializer.getStorage().get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotFound() {
        objectStreamSerializer.getStorage().get(UUID_05);
    }

    @Test
    public void update() {
        Resume toBeUpdated = new Resume(UUID_02, FIO_02);
        objectStreamSerializer.getStorage().update(toBeUpdated);
        Assert.assertEquals(RESUME_02, objectStreamSerializer.getStorage().get(UUID_02));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotFound() {
        objectStreamSerializer.getStorage().update(RESUME_05);
    }


    @Test
    public void save() {
        objectStreamSerializer.getStorage().save(RESUME_04);
        assertSize(4);
        assertGet(RESUME_04);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        objectStreamSerializer.getStorage().save(RESUME_01);
    }


    @Test
    public void getAll() {
        List<Resume> allResume = Arrays.asList(RESUME_01, RESUME_02, RESUME_03);
        Assert.assertEquals(allResume, objectStreamSerializer.getStorage().getAllSorted());

    }


    @Test
    public void clear() {
        objectStreamSerializer.getStorage().clear();
        assertSize(0);
        List<Resume> allResume = new ArrayList<>();
        Assert.assertEquals(allResume, objectStreamSerializer.getStorage().getAllSorted());
    }


    @Test(expected = NotExistStorageException.class)
    public void delete() {
        objectStreamSerializer.getStorage().delete(UUID_02);
        assertSize(2);
        objectStreamSerializer.getStorage().get(UUID_02);
    }

    @Test()
    public void saveOverflow() {}
}
