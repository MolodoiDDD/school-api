package com.school.school_api.exception;

public class SubjectNotFoundException extends RuntimeException {

    public SubjectNotFoundException(Long id) {
        super("Предмет с id " + id + " не найден");
    }
}
