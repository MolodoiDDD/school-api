package com.school.school_api.service;

import com.school.school_api.dto.SubjectCreateDto;
import com.school.school_api.dto.SubjectUpdateDto;
import com.school.school_api.entity.Lesson;
import com.school.school_api.entity.Subject;
import com.school.school_api.exception.ConflictException;
import com.school.school_api.exception.SubjectNotFoundException;
import com.school.school_api.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository repository;


    public List<Subject> findAll() {
        return repository.findAll();
    }


    public Subject findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(id));
    }

    public Subject create(SubjectCreateDto dto) {
        boolean conflict = repository.existsBySubjectName(dto.getSubjectName());
        if (conflict) {
            throw new ConflictException("Такой предмет уже существует");
        }

        Subject subject = new Subject();

        subject.setSubjectName(dto.getSubjectName());

        subject.setCreated(LocalDateTime.now());
        subject.setModified(LocalDateTime.now());
        subject.setDeleted(false);

        return  repository.save(subject);
    }

    public Subject update(Long id, SubjectUpdateDto dto) {
        boolean conflict = repository.existsBySubjectNameAndIdNot(dto.getSubjectName(), id);
        if (conflict) {
            throw new ConflictException("Такой предмет уже существует");
        }
        Subject existing = findById(id);

        existing.setSubjectName(dto.getSubjectName());
        existing.setModified(LocalDateTime.now());

        return repository.save(existing);
    }
    
    public Subject delete(Long id) {
        Subject subject = findById(id);

        subject.setDeleted(true);
        subject.setModified(LocalDateTime.now());
        return repository.save(subject);
    }


}
