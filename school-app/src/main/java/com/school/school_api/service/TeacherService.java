package com.school.school_api.service;

import com.school.school_api.dto.TeacherCreateDto;
import com.school.school_api.dto.TeacherUpdateDto;
import com.school.school_api.entity.Teacher;
import com.school.school_api.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public List<Teacher> findAll() {
        return repository.findAll();
    }

    public Teacher findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Учитель не найден"));
    }

    public Teacher create(TeacherCreateDto dto) {
        Teacher teacher = new Teacher();

        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setMiddleName(dto.getMiddleName());

        teacher.setCreated(LocalDateTime.now());
        teacher.setModified(LocalDateTime.now());
        teacher.setDeleted(false);

        return repository.save(teacher);
    }

    public Teacher update(Long id, TeacherUpdateDto dto) {
        Teacher existing = findById(id);

        if (dto.getFirstName() != null) {
            existing.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() != null) {
            existing.setLastName(dto.getLastName());
        }

        if (dto.getMiddleName() != null) {
            existing.setMiddleName(dto.getMiddleName());
        }

        existing.setModified(LocalDateTime.now());

        return repository.save(existing);
    }

    public Teacher delete(Long id) {
        Teacher teacher = findById(id);

        teacher.setDeleted(true);
        teacher.setModified(LocalDateTime.now());

        return repository.save(teacher);
    }

}
