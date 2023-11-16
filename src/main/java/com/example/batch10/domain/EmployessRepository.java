package com.example.batch10.domain;

import com.example.batch10.domain.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployessRepository extends JpaRepository<Employees, Long>  {

}
