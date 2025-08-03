package com.example.likelion13th_spring.service;

import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Page<Member> getMembersByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return memberRepository.findAll(pageable);
    }

    public Page<Member> getAdultMembersSortedByName(int page, int size, Integer age) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return memberRepository.findByAgeGreaterThanEqualOrderByName(age, pageable);
    }

    public List<Member> getMembersByNamePrefix(String prefix) {
        return memberRepository.findByNameStartingWith(prefix);
    }
}
