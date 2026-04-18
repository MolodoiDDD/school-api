package com.school.school_api.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LessonNotFoundException.class)
    public ResponseEntity<String> handleLessonNotFound(LessonNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<String> handleTeacherNotFound(TeacherNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<String> handleRoomNotFound(RoomNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SchoolClassNotFoundException.class)
    public ResponseEntity<String> handleSchoolClassNotFound(SchoolClassNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<String> handleSubjectNotFound(SubjectNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {

        return ResponseEntity.badRequest().body(
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        );

    }



    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflict(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAny(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Внутреняя ошибка сервера");
    }


}
