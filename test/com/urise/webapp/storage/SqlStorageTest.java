package com.urise.webapp.storage;

import com.urise.webapp.Config;
import org.junit.Test;

public class SqlStorageTest extends AbstractStorageTest {

    private SqlStorage sqlStorage;

    public SqlStorageTest() {
        super(new SqlStorage(
                Config.get().getProps().getProperty("db.url"),
                Config.get().getProps().getProperty("db.user"),
                Config.get().getProps().getProperty("db.password")));
        this.sqlStorage = (SqlStorage) super.storage;
    }

    @Test()
    public void saveOverflow() {}
}
