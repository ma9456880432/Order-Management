package com.twinkleaves.ordermanagement.repository;

import com.twinkleaves.ordermanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
