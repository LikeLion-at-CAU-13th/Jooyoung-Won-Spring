package com.example.likelion13th_spring.repository;

import com.example.likelion13th_spring.domain.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsBySellerName(String name);
}
