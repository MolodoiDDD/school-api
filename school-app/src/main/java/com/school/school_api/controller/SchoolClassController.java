package com.school.school_api.controller;

import com.school.school_api.dto.ScheduleLessonDto;
import com.school.school_api.dto.SchoolClassCreateDto;
import com.school.school_api.dto.SchoolClassUpdateDto;
import com.school.school_api.entity.Lesson;
import com.school.school_api.entity.SchoolClass;
import com.school.school_api.service.SchoolClassService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
public class SchoolClassController {

    private final SchoolClassService service;


    @GetMapping
    public List<SchoolClass> getAll() {
        return service.findAll();
    }

    @GetMapping
    public Page<SchoolClass> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public SchoolClass getById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public SchoolClass create(@Valid @RequestBody SchoolClassCreateDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public SchoolClass update(@PathVariable("id") Long id, @Valid @RequestBody SchoolClassUpdateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public SchoolClass delete(@PathVariable("id") Long id) {
        return service.delete(id);
    }

}
