package com.example.ecommerce1.repository;

import com.example.ecommerce1.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
