package com.school.school_api.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super("Такая запись уже существует");
    }
}
