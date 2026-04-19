package com.school.school_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDto {

    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;

    public String getTeacherFullName() {

        StringBuilder fullName = new StringBuilder();

        fullName.append(lastName);
        fullName.append(" ");
        fullName.append(firstName);

        if (middleName != null && !middleName.isBlank()) {
            fullName.append(" ");
            fullName.append(middleName);
        }

        return fullName.toString();
    }
}
