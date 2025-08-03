package com.example.likelion13th_spring.domain;

import com.example.likelion13th_spring.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String email;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role; // 판매자면 SELLER, 구매자면 BUYER

    private Boolean isAdmin; // 관리자 계정 여부

    private Integer deposit; // 현재 계좌 잔액

    private Integer age; // 나이 필드 추가

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    public void chargeDeposit(int money){
        this.deposit += money;
    }
    public void useDeposit(int money) {
        this.deposit -= money;
    }

    @Builder
    public Member(String name, String address, String email, String phoneNumber,
                  Role role, Boolean isAdmin, Integer deposit, Integer age) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.isAdmin = isAdmin;
        this.deposit = deposit;
        this.age = age;
    }
}