package com.school.school_api.exception;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException(Long id) {
        super("Урок с id " + id + " не найден");
    }
}
