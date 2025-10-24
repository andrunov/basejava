package com.urise.webapp.exception;

public class ImmutableResumeException extends RuntimeException {
    public ImmutableResumeException(String uuid) {
        super("Зарезервированные резюме нельзя менять. Resume UUID: " + uuid);
    }
}
