package com.school.school_api.service;

import com.school.school_api.dto.LessonCreateDto;
import com.school.school_api.dto.LessonUpdateDto;
import com.school.school_api.dto.ScheduleLessonDto;
import com.school.school_api.dto.TeacherDto;
import com.school.school_api.entity.Lesson;
import com.school.school_api.exception.*;
import com.school.school_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository repository;
    private final RoomRepository roomRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;


    public List<Lesson> findAll() {
        return repository.findAll();
    }



    public Lesson findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException(id));
    }

    public Lesson create(LessonCreateDto dto) {
        Lesson lesson = Lesson.builder()
                .subject(subjectRepository.findById(dto.getSubjectId())
                        .orElseThrow(() -> new SubjectNotFoundException(dto.getSubjectId())))
                .room(roomRepository.findById(dto.getRoomId())
                        .orElseThrow(() -> new RoomNotFoundException(dto.getRoomId())))
                .schoolClass(schoolClassRepository.findById(dto.getClassId())
                        .orElseThrow(() -> new SchoolClassNotFoundException(dto.getClassId())))
                .teacher(teacherRepository.findById(dto.getTeacherId())
                        .orElseThrow(() -> new TeacherNotFoundException(dto.getTeacherId())))
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .deleted(false)
                .build();

        return repository.save(lesson);
    }

    public Lesson update(Long id, LessonUpdateDto dto) {
        Lesson existing = findById(id);

        if (dto.getRoomId() != null) {
            existing.setRoom(roomRepository.findById(dto.getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException(dto.getRoomId())));
        }

        if (dto.getClassId() != null) {
            existing.setSchoolClass(schoolClassRepository.findById(dto.getClassId())
                    .orElseThrow(() -> new SchoolClassNotFoundException(dto.getClassId())));
        }

        if (dto.getSubjectId() != null) {
            existing.setSubject(subjectRepository.findById(dto.getSubjectId())
                    .orElseThrow(() -> new SubjectNotFoundException(dto.getSubjectId())));
        }
        if (dto.getTeacherId() != null) {
            existing.setTeacher(teacherRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new TeacherNotFoundException(dto.getTeacherId())));
        }

        if (dto.getStartTime() != null) {
            existing.setStartTime(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            existing.setEndTime(dto.getEndTime());
        }

        existing.setModified(LocalDateTime.now());

        return repository.save(existing);
    }

    public Lesson delete(Long id) {
        Lesson lesson = findById(id);

        lesson.setDeleted(true);
        lesson.setModified(LocalDateTime.now());

        return repository.save(lesson);
    }

    private ScheduleLessonDto toDto(Lesson lesson) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(lesson.getTeacher().getId());
        teacherDto.setLastName(lesson.getTeacher().getLastName());
        teacherDto.setFirstName(lesson.getTeacher().getFirstName());
        teacherDto.setMiddleName(lesson.getTeacher().getMiddleName());

        return new ScheduleLessonDto(
                lesson.getDayOfWeek(),
                lesson.getSubject().getSubjectName(),
                teacherDto.getTeacherFullName(),
                lesson.getStartTime(),
                lesson.getEndTime(),
                lesson.getRoom().getRoomNumber(),
                lesson.getSchoolClass().getName()
        );
    }

    public List<ScheduleLessonDto> getSchedule() {
        List<ScheduleLessonDto> schedule = new ArrayList<>();

        for (Lesson lesson : repository.findAll()) {
            if (lesson.getDeleted()) {
                continue;
            }
            schedule.add(toDto(lesson));
        }

        return schedule;
    }

    public List<ScheduleLessonDto> getScheduleByClassAndDate(Long classId, LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();

        List<ScheduleLessonDto> schedule = new ArrayList<>();

        for (Lesson lesson : repository.findAll()) {
            if (lesson.getDeleted()) {
                continue;
            }

            if (!lesson.getSchoolClass().getId().equals(classId)) {
                continue;
            }

            if (lesson.getDayOfWeek() != dayOfWeek) {
                continue;
            }

            schedule.add(toDto(lesson));
        }
        return schedule;
    }
}
