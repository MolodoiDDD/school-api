package com.school.school_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class LessonUpdateDto {


    private Long classId;
    private Long teacherId;
    private Long subjectId;
    private Long roomId;


    @Min(value = 1, message = "День недели должен быть от 1 до 7")
    @Max(value = 7, message = "День недели должен быть от 1 до 7")
    private Integer dayOfWeek;



    private LocalTime startTime;
    private LocalTime endTime;
}
