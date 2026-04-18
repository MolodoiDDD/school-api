package com.school.school_api.controller;

import com.school.school_api.dto.RoomCreateDto;
import com.school.school_api.dto.RoomUpdateDto;
import com.school.school_api.entity.Lesson;
import com.school.school_api.entity.Room;
import com.school.school_api.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;

    @GetMapping
    public List<Room> getAll() {
        return service.findAll();
    }


    @GetMapping("/{id}")
    public Room getById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Room create(@Valid @RequestBody RoomCreateDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Room update(@PathVariable("id") Long id, @Valid @RequestBody RoomUpdateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Room delete(@PathVariable("id") Long id) {
        return service.delete(id);
    }


}
