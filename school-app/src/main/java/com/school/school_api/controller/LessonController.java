package com.school.school_api.controller;

import com.school.school_api.dto.LessonCreateDto;
import com.school.school_api.dto.LessonUpdateDto;
import com.school.school_api.dto.ScheduleLessonDto;
import com.school.school_api.entity.Lesson;
import com.school.school_api.service.LessonService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService service;

    public LessonController(LessonService service) {
        this.service = service;
    }

    @GetMapping
    public List<Lesson> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Lesson getById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Lesson create(@Valid @RequestBody LessonCreateDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Lesson update(@PathVariable("id") Long id, @Valid @RequestBody LessonUpdateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Lesson delete(@PathVariable("id") Long id) {
        return service.delete(id);
    }

    @GetMapping("/schedule")
    public List<ScheduleLessonDto> getSchedule() {
        return service.getSchedule();
    }

    @GetMapping("/{id}/schedule")
    public List<ScheduleLessonDto> getScheduleByClassAndDate(
            @PathVariable("id") Long id,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        return service.getScheduleByClassAndDate(id, date);
    }

}
