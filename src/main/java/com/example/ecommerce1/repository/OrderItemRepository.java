package com.example.ecommerce1.repository;

import com.example.ecommerce1.domain.Order;
import com.example.ecommerce1.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
