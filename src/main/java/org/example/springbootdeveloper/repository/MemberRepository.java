package org.example.springbootdeveloper.repository;

import org.example.springbootdeveloper.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
