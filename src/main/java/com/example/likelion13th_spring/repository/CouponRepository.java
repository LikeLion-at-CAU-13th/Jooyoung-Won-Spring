package com.example.likelion13th_spring.repository;

import com.example.likelion13th_spring.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
