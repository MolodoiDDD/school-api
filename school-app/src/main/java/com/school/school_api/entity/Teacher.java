package com.school.school_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name", nullable = false, length = 254)
    private String lastName;

    @Column(name = "first_name", nullable = false, length = 254)
    private String firstName;

    @Column(name = "middle_name", length = 254)
    private String middleName;


    private LocalDateTime created;
    private LocalDateTime modified;
    private Boolean deleted;

}
