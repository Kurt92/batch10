package com.example.batch10.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.batch10.domain.QEmployees.employees;


@Repository
@RequiredArgsConstructor
public class SalariesQueryDslRepository implements ISalariesQueryDslRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public Long findCustomerTotalCount() {
        Long count = jpaQueryFactory
                .select(employees.empNo)
                .from(employees)
                .fetchCount();
        return count;
    }
}
