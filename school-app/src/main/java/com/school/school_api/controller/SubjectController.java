package com.school.school_api.controller;

import com.school.school_api.dto.SubjectCreateDto;
import com.school.school_api.dto.SubjectUpdateDto;
import com.school.school_api.entity.Lesson;
import com.school.school_api.entity.Subject;
import com.school.school_api.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService service;

    @GetMapping
    public List<Subject> getAll() {
        return service.findAll();
    }

    @GetMapping
    public Page<Subject> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Subject getById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Subject create(@Valid @RequestBody SubjectCreateDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Subject update(@PathVariable("id") Long id, @Valid @RequestBody SubjectUpdateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Subject delete(@PathVariable("id") Long id) {
        return service.delete(id);
    }
}
