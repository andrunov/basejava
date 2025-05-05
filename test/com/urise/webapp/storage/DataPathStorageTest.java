package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.DataStreamSerializer;
import org.junit.Test;

import static com.urise.webapp.storage.TestData.STORAGE_DIR;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }

    @Test()
    public void saveOverflow() {}
}