package com.school.school_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherCreateDto {

    @NotBlank(message = "Имя учителя не может отсутствовать")
    @Size(max = 254, message = "Имя учителя не должно быть длиннее 254 символов")
    private String firstName;

    @NotBlank(message = "Фамилия учителя не может отсутствовать")
    @Size(max = 254, message = "Фамилия учителя не должно быть длиннее 254 символов")
    private String lastName;

    @Size(max = 254, message = "Отчество учителя не должно быть длиннее 254 символов")
    private String middleName;

}
