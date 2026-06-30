package com.example.ecommerce1.service;

import com.example.ecommerce1.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void createCart(Long userId, Long productId) {

    }
}
