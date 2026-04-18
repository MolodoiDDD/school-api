package com.school.school_api.exception;

public class SchoolClassNotFoundException extends RuntimeException {

    public SchoolClassNotFoundException(Long id) {
        super("Класс с id " + id + " не найден");
    }
}
