package com.example.batch10.domain;

import com.example.batch10.constants.GenderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Employees implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empNo;

    private LocalDate birthDate;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private GenderStatus gender;

    private LocalDate hireDate;

    private String deleteYn;



}
