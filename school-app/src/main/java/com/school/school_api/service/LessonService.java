package com.school.school_api.service;

import com.school.school_api.dto.LessonCreateDto;
import com.school.school_api.dto.LessonUpdateDto;
import com.school.school_api.dto.ScheduleLessonDto;
import com.school.school_api.entity.Lesson;
import com.school.school_api.entity.Subject;
import com.school.school_api.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LessonService {

    private final LessonRepository repository;
    private final RoomRepository roomRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    public LessonService(LessonRepository repository,
                         RoomRepository roomRepository,
                         SchoolClassRepository schoolClassRepository,
                         SubjectRepository subjectRepository,
                         TeacherRepository teacherRepository) {
        this.repository = repository;
        this.roomRepository = roomRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }


    public List<Lesson> findAll() {
        return repository.findAll();
    }

    public Lesson findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Расписание не найдено"));
    }

    public Lesson create(LessonCreateDto dto) {
        Lesson lesson = new Lesson();

        lesson.setSubject(subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Предмет не найден")));

        lesson.setRoom(roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Кабинет не найден")));

        lesson.setSchoolClass(schoolClassRepository.findById(dto.getClassId())
                .orElseThrow(() -> new RuntimeException("Класс не найден")));

        lesson.setTeacher(teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Учитель не найден")));

        lesson.setStartTime(dto.getStartTime());
        lesson.setEndTime(dto.getEndTime());

        lesson.setCreated(LocalDateTime.now());
        lesson.setModified(LocalDateTime.now());
        lesson.setDeleted(false);

        return repository.save(lesson);
    }

    public Lesson update(Long id, LessonUpdateDto dto) {
        Lesson existing = findById(id);

        if (dto.getRoomId() != null) {
            existing.setRoom(roomRepository.findById(dto.getRoomId())
                    .orElseThrow(() -> new RuntimeException("Кабинет не найден")));
        }

        if (dto.getClassId() != null) {
            existing.setSchoolClass(schoolClassRepository.findById(dto.getClassId())
                    .orElseThrow(() -> new RuntimeException("Класс не найден")));
        }

        if (dto.getSubjectId() != null) {
            existing.setSubject(subjectRepository.findById(dto.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Предмет не найден")));
        }
        if (dto.getTeacherId() != null) {
            existing.setTeacher(teacherRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Учитель не найден")));
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

    public List<ScheduleLessonDto> getSchedule() {
        List<ScheduleLessonDto> schedule = new ArrayList<>();

        for (Lesson lesson : repository.findAll()) {
            if (lesson.getDeleted()) {
                continue;
            }
            ScheduleLessonDto dto = new ScheduleLessonDto(
                    lesson.getDayOfWeek(),
                    lesson.getSubject().getSubjectName(),
                    lesson.getTeacher().getLastName() + " " +
                            lesson.getTeacher().getFirstName() + " " +
                            lesson.getTeacher().getMiddleName(),
                    lesson.getStartTime(),
                    lesson.getEndTime(),
                    lesson.getRoom().getRoomNumber(),
                    lesson.getSchoolClass().getName()
            );
            schedule.add(dto);
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

            ScheduleLessonDto dto = new ScheduleLessonDto(
                    lesson.getDayOfWeek(),
                    lesson.getSubject().getSubjectName(),
                    lesson.getTeacher().getLastName() + " " +
                            lesson.getTeacher().getFirstName() + " " +
                            lesson.getTeacher().getMiddleName(),
                    lesson.getStartTime(),
                    lesson.getEndTime(),
                    lesson.getRoom().getRoomNumber(),
                    lesson.getSchoolClass().getName()
            );
            schedule.add(dto);
        }
        return schedule;
    }
}
