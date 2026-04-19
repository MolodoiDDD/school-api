package com.school.school_api.exception;

public class TeacherNotFoundException extends RuntimeException {

    public TeacherNotFoundException(Long id) {
        super("Учитель с id " + id + " не найден");
    }
}
