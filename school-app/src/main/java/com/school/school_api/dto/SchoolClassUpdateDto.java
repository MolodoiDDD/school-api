package com.school.school_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolClassUpdateDto {

    @NotBlank(message = "Номер класса не может быть пустым")
    @Size(max = 3, message = "Номер класса должен быть до 3 символов")
    private String name;
}
