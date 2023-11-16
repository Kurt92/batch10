package com.example.batch10.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@IdClass(SalariesId.class)
public class Salaries implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "emp_no", nullable = false)
    private Employees employee;

    @Id
    private LocalDate fromDate;

    private int salary;

    private LocalDate toDate;
}