package com.example.ecommerce1.repository;

import com.example.ecommerce1.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
