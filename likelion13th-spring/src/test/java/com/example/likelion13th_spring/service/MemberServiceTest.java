package com.example.likelion13th_spring.service;

import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.enums.Role;
import com.example.likelion13th_spring.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();

        IntStream.rangeClosed(1, 30).forEach(i -> {
            Member member = Member.builder()
                .name("user" + i)
                .email("user" + i + "@test.com")
                .address("서울시 테스트동 " + i + "번지")
                .phoneNumber("010-1234-56" + String.format("%02d", i))
                .deposit(1000 * i)
                .isAdmin(false)
                .role(Role.BUYER)
                .age(i)
                .build();

            memberRepository.save(member);
        });
    }

    @Test
    void testGetMembersByPage() {
        Page<Member> page = memberService.getMembersByPage(0, 10);

        assertThat(page.getContent()).hasSize(10);
        assertThat(page.getTotalElements()).isEqualTo(30);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.getContent().get(0).getName()).isEqualTo("user1");
    }

    @Test
    void testGetAdultMembersSortedByName() {
        Page<Member> page = memberService.getAdultMembersSortedByName(0, 10, 20);

        assertThat(page.getContent()).hasSize(10);
        assertThat(page.getTotalElements()).isEqualTo(11);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.getContent().get(0).getAge()).isEqualTo(20);
    }

    @Test
    void testGetMembersByNamePrefix() {
        List<Member> members = memberService.getMembersByNamePrefix("user");
        List<Member> members2 = memberService.getMembersByNamePrefix("user1");
        List<Member> members3 = memberService.getMembersByNamePrefix("user20");

        // 정렬을 하지 않았기 때문에 개수로만 비교
        assertThat(members.size()).isEqualTo(30);
        assertThat(members2.size()).isEqualTo(11);
        assertThat(members3.size()).isEqualTo(1);
    }
}


