package com.school.school_api.repository;

import com.school.school_api.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("""
        select case when count(l) > 0 then true else false end
        from Lesson l
        where l.deleted = false
          and l.dayOfWeek = :dayOfWeek
          and l.room.id = :roomId
          and l.startTime < :endTime
          and l.endTime > :startTime
    """)
    boolean existsRoomConflict(@Param("roomId") Long roomId,
                               @Param("dayOfWeek") Integer dayOfWeek,
                               @Param("startTime") LocalTime startTime,
                               @Param("endTime") LocalTime endTime);

    @Query("""
        select case when count(l) > 0 then true else false end
        from Lesson l
        where l.deleted = false
          and l.dayOfWeek = :dayOfWeek
          and l.teacher.id = :teacherId
          and l.startTime < :endTime
          and l.endTime > :startTime
    """)
    boolean existsTeacherConflict(@Param("teacherId") Long teacherId,
                                  @Param("dayOfWeek") Integer dayOfWeek,
                                  @Param("startTime") LocalTime startTime,
                                  @Param("endTime") LocalTime endTime);

    @Query("""
        select case when count(l) > 0 then true else false end
        from Lesson l
        where l.deleted = false
          and l.dayOfWeek = :dayOfWeek
          and l.schoolClass.id = :classId
          and l.startTime < :endTime
          and l.endTime > :startTime
    """)
    boolean existsClassConflict(@Param("classId") Long classId,
                                @Param("dayOfWeek") Integer dayOfWeek,
                                @Param("startTime") LocalTime startTime,
                                @Param("endTime") LocalTime endTime);
}
