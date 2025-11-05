package com.example.likelion13th_spring.repository;

import com.example.likelion13th_spring.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    Optional<Member> findByEmail(String email);

    Page<Member> findByAgeGreaterThanEqualOrderByName(Integer age, Pageable pageable);
    List<Member> findByNameStartingWith(String prefix);

    boolean existsByName(String name);
}
