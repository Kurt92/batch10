package com.example.batch10.repository;

import com.example.batch10.domain.Member;
import com.example.batch10.domain.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAll();
    List<Member> findByStatusEquals(MemberStatus memberStatus);
}
