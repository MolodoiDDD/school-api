package com.school.school_api.service;

import com.school.school_api.dto.RoomCreateDto;
import com.school.school_api.dto.RoomUpdateDto;
import com.school.school_api.entity.Lesson;
import com.school.school_api.entity.Room;
import com.school.school_api.exception.RoomNotFoundException;
import com.school.school_api.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository repository;

    public List<Room> findAll() {
        return repository.findAll();
    }

    public Page<Room> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Room findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
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
