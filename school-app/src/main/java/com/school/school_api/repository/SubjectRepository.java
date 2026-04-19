package com.school.school_api.repository;

import com.school.school_api.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    boolean existsBySubjectName(String subjectName);
    boolean existsBySubjectNameAndIdNot(String subjectName, Long id);
}
