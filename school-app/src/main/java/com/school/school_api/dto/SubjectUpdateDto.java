package com.school.school_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectUpdateDto {

    @NotBlank(message = "Название предмета не может быть пустым")
    @Size(max = 254, message = "Название предмета не может быть больше 254 символов")
    private String subjectName;
}
