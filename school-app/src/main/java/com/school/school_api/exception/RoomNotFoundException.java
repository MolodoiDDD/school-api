package com.school.school_api.exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(Long id) {
        super("Кабинет с id " + id + " не найден");
    }
}
