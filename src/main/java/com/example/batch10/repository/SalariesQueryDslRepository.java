package com.example.batch10.repository;

import com.example.batch10.domain.Employees;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class SalariesQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

//    public Long findCustomerTotalCount(Employees employees) {
//        Long count = jpaQueryFactory
//                .select(employees.empNo())  // empNo 필드를 직접 선택
//                .from(employees)
//                .fetchCount();
//        return count;
//    }
}
