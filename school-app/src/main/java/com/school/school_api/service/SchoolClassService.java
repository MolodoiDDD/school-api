package com.school.school_api.service;

import com.school.school_api.dto.SchoolClassCreateDto;
import com.school.school_api.dto.SchoolClassUpdateDto;
import com.school.school_api.entity.SchoolClass;
import com.school.school_api.repository.SchoolClassRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchoolClassService {

    private final SchoolClassRepository repository;

    public SchoolClassService(SchoolClassRepository repository) {
        this.repository = repository;
    }

    public List<SchoolClass> findAll() {
        return repository.findAll();
    }

    public SchoolClass findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Класс не найден:" + id));
    }

    public SchoolClass create(SchoolClassCreateDto dto) {
        SchoolClass schoolClass = new SchoolClass();

        schoolClass.setName(dto.getName());
        schoolClass.setCreated(LocalDateTime.now());
        schoolClass.setModified(LocalDateTime.now());
        schoolClass.setDeleted(false);

        return repository.save(schoolClass);
    }

    public SchoolClass update(Long id, SchoolClassUpdateDto dto) {
        SchoolClass schoolClass = repository.findById(id).orElseThrow(() -> new RuntimeException("Класс не найден"));

        schoolClass.setName(dto.getName());
        schoolClass.setModified(LocalDateTime.now());

        return repository.save(schoolClass);
    }

    public SchoolClass delete(Long id) {
        SchoolClass existing = findById(id);
        existing.setDeleted(true);
        existing.setModified(LocalDateTime.now());

        return repository.save(existing);
    }
}
