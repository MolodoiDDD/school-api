package com.school.school_api.service;

import com.school.school_api.dto.TeacherCreateDto;
import com.school.school_api.dto.TeacherUpdateDto;
import com.school.school_api.entity.Lesson;
import com.school.school_api.entity.Teacher;
import com.school.school_api.exception.ConflictException;
import com.school.school_api.exception.TeacherNotFoundException;
import com.school.school_api.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository repository;


    public List<Teacher> findAll() {
        return repository.findAll();
    }


    public Teacher findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
    }

    public Teacher create(TeacherCreateDto dto) {
        boolean conflict = repository.existsByLastNameAndFirstNameAndMiddleName(
                dto.getLastName(),
                dto.getFirstName(),
                dto.getMiddleName()
        );

        if (conflict) {
            throw new ConflictException("Такой учитель уже существует");
        }

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
        boolean conflict = repository.existsByLastNameAndFirstNameAndMiddleNameAndIdNot(
                dto.getLastName(),
                dto.getFirstName(),
                dto.getMiddleName(),
                id
        );
        if (conflict) {
            throw new ConflictException("Такой учитель уже существует");
        }

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
