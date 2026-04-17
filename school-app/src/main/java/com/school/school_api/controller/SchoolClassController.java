package com.school.school_api.controller;

import com.school.school_api.dto.SchoolClassCreateDto;
import com.school.school_api.dto.SchoolClassUpdateDto;
import com.school.school_api.entity.SchoolClass;
import com.school.school_api.service.SchoolClassService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class SchoolClassController {

    private final SchoolClassService service;

    public SchoolClassController(SchoolClassService service) {
        this.service = service;
    }

    @GetMapping
    public List<SchoolClass> getAll() {
        return service.findAll();
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
