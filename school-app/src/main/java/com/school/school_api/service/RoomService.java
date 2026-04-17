package com.school.school_api.service;

import com.school.school_api.dto.RoomCreateDto;
import com.school.school_api.dto.RoomUpdateDto;
import com.school.school_api.entity.Room;
import com.school.school_api.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> findAll() {
        return repository.findAll();
    }
    public Room findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Кабинет не найден"));
    }

    public Room create(RoomCreateDto dto) {
        Room room = new Room();

        room.setRoomNumber(dto.getRoomNumber());
        room.setCreated(LocalDateTime.now());
        room.setModified(LocalDateTime.now());
        room.setDeleted(false);

        return repository.save(room);
    }

    public Room update(Long id, RoomUpdateDto dto) {
        Room existing = findById(id);

        existing.setRoomNumber(dto.getRoomNumber());
        existing.setModified(LocalDateTime.now());

        return repository.save(existing);
    }

    public Room delete(Long id) {
        Room room = findById(id);

        room.setDeleted(true);
        room.setModified(LocalDateTime.now());

        return repository.save(room);
    }
}
