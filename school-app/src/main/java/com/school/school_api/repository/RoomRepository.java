package com.school.school_api.repository;

import com.school.school_api.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByRoomNumber(Integer roomName);
    boolean existsByRoomNumberAndIdNot(Integer roomName, Long id);
}
