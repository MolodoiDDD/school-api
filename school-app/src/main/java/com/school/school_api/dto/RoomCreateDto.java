package com.school.school_api.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomCreateDto {

    @NotNull(message = "Номер кабинета не может быть пустым")
    @Min(value = 1, message = "Номер кабинета не должен быть меньше 1")
    @Max(value = 999, message = "Номер кабинета не должен быть больше 999")
    private Integer roomNumber;
}
