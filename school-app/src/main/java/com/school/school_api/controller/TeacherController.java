package com.school.school_api.controller;

import com.school.school_api.dto.TeacherCreateDto;
import com.school.school_api.dto.TeacherUpdateDto;
import com.school.school_api.entity.Teacher;
import com.school.school_api.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService service;

    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    public List<Teacher> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Teacher getById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Teacher create(@Valid @RequestBody TeacherCreateDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Teacher update(@PathVariable("id") Long id, @Valid @RequestBody TeacherUpdateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Teacher delete(@PathVariable("id") Long id) {
        return service.delete(id);
    }
}
