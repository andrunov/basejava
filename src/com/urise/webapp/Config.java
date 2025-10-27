package com.urise.webapp;

import com.urise.webapp.exception.ImmutableResumeException;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Config {
    private static final File PROPS =  new File("/opt/tomcat/latest/conf/resumes.properties");
    private static final Config INSTANCE = new Config();

    private final Properties props = new Properties();
    private final File storageDir;
    private final Storage storage;

    private Set<String> immutableUuids = new HashSet<String>() {{  // for JDK 9+: Set.of("111", "222");
        add("3fdf7eac-3025-4d3e-9d2b-64e4143d3880");
        add("d24ec8ae-5926-479e-af63-009c15c8527b");
        add("b4e1b816-8656-4af0-ab31-47f9d2b884bb");
        add("c2602af8-6efa-46d2-9539-af11786e3eaa");
        add("4e90c472-6fc1-43f8-b9dc-1840192aa890");
    }};

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = Files.newInputStream(PROPS.toPath())) {
            props.load(is);
            storageDir = null;
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Properties getProps() {
        return props;
    }

    private static File getHomeDir() {
        String prop = System.getProperty("homeDir");
        File homeDir = new File(prop == null ? "." : prop);
        if (!homeDir.isDirectory()) {
            throw new IllegalStateException(homeDir + " is not directory");
        }
        return homeDir;
    }

    public Storage getStorage() {
        return storage;
    }

    public boolean isImmutable(String uuids) {
        return immutableUuids.contains(uuids);
    }

    public void checkImmutable(String uuids) {
        if (immutableUuids.contains(uuids))
            throw new ImmutableResumeException(uuids);
    }
}
