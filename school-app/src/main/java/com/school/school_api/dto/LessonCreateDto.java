package com.school.school_api.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class LessonCreateDto {


    @NotNull(message = "Класс не может быть пустым")
    private Long classId;

    @NotNull(message = "Учитель не может быть пустым")
    private Long teacherId;

    @NotNull(message = "Предмет не может быть пустым")
    private Long subjectId;

    @NotNull(message = "Кабинет не может быть пустым")
    private Long roomId;


    @NotNull(message = "День недели не может быть пустым")
    @Min(value = 1, message = "День недели должен быть от 1 до 7")
    @Max(value = 7, message = "День недели должен быть от 1 до 7")
    private Integer dayOfWeek;


    @NotNull(message = "Время начала урока не может быть пустым")
    private LocalTime startTime;

    @NotNull(message = "Время окончания урока не может быть пустым")
    private LocalTime endTime;
}
