package com.example.batch10.domain;

import com.example.batch10.repository.ISalariesQueryDslRepository;
import com.example.batch10.repository.SalariesQueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployessRepository extends JpaRepository<Employees, Long> , ISalariesQueryDslRepository {

}
