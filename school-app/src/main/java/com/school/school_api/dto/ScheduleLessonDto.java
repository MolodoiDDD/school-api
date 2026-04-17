package com.school.school_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleLessonDto {

    private Integer dayOfWeek;
    private String subjectName;
    private String teacherFullName;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer roomNumber;
    private String schoolClass;
}
