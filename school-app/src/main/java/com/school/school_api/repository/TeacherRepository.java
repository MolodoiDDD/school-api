package com.school.school_api.repository;

import com.school.school_api.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByLastNameAndFirstNameAndMiddleName(String lastName, String firstName, String middleName);
    boolean existsByLastNameAndFirstNameAndMiddleNameAndIdNot(String lastName, String firstName, String middleName, Long id);
}
