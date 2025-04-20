package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.storage.TestData.*;

public class MapUuidStorageTest extends AbstractStorageTest{

    public MapUuidStorageTest() {
        super(new MapUuidStorage());
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

    @Test()
    public void saveOverflow() {}

}
