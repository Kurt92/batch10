package com.example.batch10.domain;

import com.example.batch10.constants.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalariesRepository extends JpaRepository<Salaries, Long> {

}
