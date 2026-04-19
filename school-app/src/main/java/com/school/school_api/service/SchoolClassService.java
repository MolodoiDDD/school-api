package com.school.school_api.service;

import com.school.school_api.dto.SchoolClassCreateDto;
import com.school.school_api.dto.SchoolClassUpdateDto;
import com.school.school_api.entity.Lesson;
import com.school.school_api.entity.SchoolClass;
import com.school.school_api.exception.ConflictException;
import com.school.school_api.exception.SchoolClassNotFoundException;
import com.school.school_api.repository.SchoolClassRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolClassService {

    private final SchoolClassRepository repository;


    public List<SchoolClass> findAll() {
        return repository.findAll();
    }


    public SchoolClass findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new SchoolClassNotFoundException(id));
    }

    public SchoolClass create(SchoolClassCreateDto dto) {
        boolean conflict = repository.existsByName(dto.getName());
        if (conflict) {
            throw new ConflictException("Такой класс уже существует");
        }
        SchoolClass schoolClass = new SchoolClass();

        schoolClass.setName(dto.getName());
        schoolClass.setCreated(LocalDateTime.now());
        schoolClass.setModified(LocalDateTime.now());
        schoolClass.setDeleted(false);

        return repository.save(schoolClass);
    }

    public SchoolClass update(Long id, SchoolClassUpdateDto dto) {
        boolean conflict = repository.existsByNameAndIdNot(dto.getName(), id);
        if (conflict) {
            throw new ConflictException("Такой класс уже существует");
        }

        SchoolClass schoolClass = findById(id);


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
