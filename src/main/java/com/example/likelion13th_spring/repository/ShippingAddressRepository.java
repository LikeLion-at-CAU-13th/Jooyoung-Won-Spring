package com.example.likelion13th_spring.repository;

import com.example.likelion13th_spring.domain.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
}
