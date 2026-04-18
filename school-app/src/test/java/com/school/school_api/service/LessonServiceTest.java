package com.school.school_api.service;

import com.school.school_api.dto.LessonCreateDto;
import com.school.school_api.dto.LessonUpdateDto;
import com.school.school_api.dto.ScheduleLessonDto;
import com.school.school_api.entity.Lesson;
import com.school.school_api.entity.Room;
import com.school.school_api.entity.SchoolClass;
import com.school.school_api.entity.Subject;
import com.school.school_api.entity.Teacher;
import com.school.school_api.exception.LessonNotFoundException;
import com.school.school_api.repository.LessonRepository;
import com.school.school_api.repository.RoomRepository;
import com.school.school_api.repository.SchoolClassRepository;
import com.school.school_api.repository.SubjectRepository;
import com.school.school_api.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {

    @Mock
    private LessonRepository repository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private SchoolClassRepository schoolClassRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private LessonService service;

    private Lesson lesson;
    private Subject subject;
    private Room room;
    private SchoolClass schoolClass;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        subject = new Subject();
        subject.setId(1L);
        subject.setSubjectName("Английский язык");

        room = new Room();
        room.setId(1L);
        room.setRoomNumber(202);

        schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("9Б");

        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("Кузнецова");
        teacher.setFirstName("Мария");
        teacher.setMiddleName("Игоревна");

        lesson = new Lesson();
        lesson.setId(1L);
        lesson.setDayOfWeek(1);
        lesson.setSubject(subject);
        lesson.setRoom(room);
        lesson.setSchoolClass(schoolClass);
        lesson.setTeacher(teacher);
        lesson.setStartTime(LocalTime.of(11, 0));
        lesson.setEndTime(LocalTime.of(11, 45));
        lesson.setDeleted(false);

    }

    @Test
    void findById_shouldReturnLesson() {
        when(repository.findById(1L)).thenReturn(Optional.of(lesson));

        Lesson result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Английский язык", result.getSubject().getSubjectName());

        verify(repository).findById(1L);
    }

    @Test
    void findById_shouldThrowWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(LessonNotFoundException.class, () -> service.findById(1L));

        verify(repository).findById(1L);
    }

    @Test
    void create_shouldSaveLesson() {
        LessonCreateDto dto = new LessonCreateDto();
        dto.setSubjectId(1L);
        dto.setRoomId(1L);
        dto.setClassId(1L);
        dto.setTeacherId(1L);
        dto.setDayOfWeek(1);
        dto.setStartTime(LocalTime.of(11, 0));
        dto.setEndTime(LocalTime.of(11, 45));

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(repository.save(any(Lesson.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Lesson result = service.create(dto);

        assertNotNull(result);
        assertEquals(subject, result.getSubject());
        assertEquals(room, result.getRoom());
        assertEquals(schoolClass, result.getSchoolClass());
        assertEquals(teacher, result.getTeacher());
        assertEquals(LocalTime.of(11, 0), result.getStartTime());
        assertEquals(LocalTime.of(11, 45), result.getEndTime());
        assertFalse(result.getDeleted());

        verify(repository).save(any(Lesson.class));
    }

    @Test
    void update_shouldChangeFields() {
        LessonUpdateDto dto = new LessonUpdateDto();
        dto.setRoomId(2L);
        dto.setClassId(2L);
        dto.setSubjectId(2L);
        dto.setTeacherId(2L);
        dto.setStartTime(LocalTime.of(12, 0));
        dto.setEndTime(LocalTime.of(12, 45));

        Room newRoom = new Room();
        newRoom.setId(2L);
        newRoom.setRoomNumber(305);

        SchoolClass newClass = new SchoolClass();
        newClass.setId(2L);
        newClass.setName("10А");

        Subject newSubject = new Subject();
        newSubject.setId(2L);
        newSubject.setSubjectName("Математика");

        Teacher newTeacher = new Teacher();
        newTeacher.setId(2L);
        newTeacher.setLastName("Иванов");
        newTeacher.setFirstName("Пётр");
        newTeacher.setMiddleName("Сергеевич");

        when(repository.findById(1L)).thenReturn(Optional.of(lesson));
        when(roomRepository.findById(2L)).thenReturn(Optional.of(newRoom));
        when(schoolClassRepository.findById(2L)).thenReturn(Optional.of(newClass));
        when(subjectRepository.findById(2L)).thenReturn(Optional.of(newSubject));
        when(teacherRepository.findById(2L)).thenReturn(Optional.of(newTeacher));
        when(repository.save(any(Lesson.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Lesson result = service.update(1L, dto);

        assertEquals(newRoom, result.getRoom());
        assertEquals(newClass, result.getSchoolClass());
        assertEquals(newSubject, result.getSubject());
        assertEquals(newTeacher, result.getTeacher());
        assertEquals(LocalTime.of(12, 0), result.getStartTime());
        assertEquals(LocalTime.of(12, 45), result.getEndTime());

        verify(repository).save(lesson);
    }

    @Test
    void delete_shouldMarkAsDeleted() {
        when(repository.findById(1L)).thenReturn(Optional.of(lesson));
        when(repository.save(any(Lesson.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Lesson result = service.delete(1L);

        assertTrue(result.getDeleted());
        
        verify(repository).save(lesson);
    }

    @Test
    void getSchedule_shouldReturnOnlyNotDeletedLessons() {
        Lesson deletedLesson = new Lesson();
        deletedLesson.setDeleted(true);

        when(repository.findAll()).thenReturn(List.of(lesson, deletedLesson));

        List<ScheduleLessonDto> result = service.getSchedule();

        assertEquals(1, result.size());
        assertEquals("Английский язык", result.get(0).getSubjectName());
        assertEquals("Кузнецова Мария Игоревна", result.get(0).getTeacherFullName());
    }

    @Test
    void getScheduleByClassAndDate_shouldFilterByClassAndDay() {
        Lesson otherClassLesson = new Lesson();
        otherClassLesson.setDeleted(false);
        otherClassLesson.setDayOfWeek(1);

        SchoolClass otherClass = new SchoolClass();
        otherClass.setId(2L);
        otherClass.setName("10А");
        otherClassLesson.setSchoolClass(otherClass);
        otherClassLesson.setSubject(subject);
        otherClassLesson.setRoom(room);
        otherClassLesson.setTeacher(teacher);
        otherClassLesson.setStartTime(LocalTime.of(10, 0));
        otherClassLesson.setEndTime(LocalTime.of(10, 45));

        when(repository.findAll()).thenReturn(List.of(lesson, otherClassLesson));

        List<ScheduleLessonDto> result = service.getScheduleByClassAndDate(1L, LocalDate.of(2024, 1, 15));

        assertEquals(1, result.size());
        assertEquals("9Б", result.get(0).getSchoolClass());
    }
}
