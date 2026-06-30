package com.example.ecommerce1.controller;

import com.example.ecommerce1.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create/{userId}/{productId}")
    public ResponseEntity<Void> createCart(@PathVariable Long userId,
                                           @PathVariable Long productId){
        cartService.createCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

}
