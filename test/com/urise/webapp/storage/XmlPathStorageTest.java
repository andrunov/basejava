package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.XmlStreamSerializer;
import org.junit.Test;

import static com.urise.webapp.storage.TestData.STORAGE_DIR;

public class XmlPathStorageTest extends AbstractStorageTest{

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }

    @Test()
    public void saveOverflow() {}

}
