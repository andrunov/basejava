package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.ObjectStreamSerializer;
import com.urise.webapp.storage.serializer.XmlStreamSerializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.storage.TestData.*;

public class XmlPathStorageTest {

    protected XmlStreamSerializer serializer;

    public XmlPathStorageTest() {
        this.serializer = new XmlStreamSerializer();
    }

    @Before
    public void setUp() {
        serializer.setStrategy(ObjectStreamSerializer.Strategy.PATH);
        serializer.getStorage().clear();
        serializer.getStorage().save(RESUME_01);
        serializer.getStorage().save(RESUME_02);
        serializer.getStorage().save(RESUME_03);
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
        Assert.assertEquals(size, serializer.getStorage().size());
    }

    protected void assertGet(Resume resume) {
        Assert.assertEquals(resume, serializer.getStorage().get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotFound() {
        serializer.getStorage().get(UUID_05);
    }

    @Test
    public void update() {
        Resume toBeUpdated = new Resume(UUID_02, FIO_02);
        serializer.getStorage().update(toBeUpdated);
        Assert.assertEquals(RESUME_02, serializer.getStorage().get(UUID_02));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotFound() {
        serializer.getStorage().update(RESUME_05);
    }


    @Test
    public void save() {
        serializer.getStorage().save(RESUME_04);
        assertSize(4);
        assertGet(RESUME_04);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        serializer.getStorage().save(RESUME_01);
    }


    @Test
    public void getAll() {
        List<Resume> allResume = Arrays.asList(RESUME_01, RESUME_02, RESUME_03);
        Assert.assertEquals(allResume, serializer.getStorage().getAllSorted());

    }


    @Test
    public void clear() {
        serializer.getStorage().clear();
        assertSize(0);
        List<Resume> allResume = new ArrayList<>();
        Assert.assertEquals(allResume, serializer.getStorage().getAllSorted());
    }


    @Test(expected = NotExistStorageException.class)
    public void delete() {
        serializer.getStorage().delete(UUID_02);
        assertSize(2);
        serializer.getStorage().get(UUID_02);
    }

    @Test()
    public void saveOverflow() {}

}
