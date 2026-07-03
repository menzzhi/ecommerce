package com.example.ecommerce1.controller;

import com.example.ecommerce1.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create/{userId}/{productId}")
    public ResponseEntity<Void> createCart(@PathVariable Long userId,
                                           @PathVariable Long productId,
                                           @RequestParam Integer quantity){
        cartService.createCart(userId, productId, quantity);
        return ResponseEntity.noContent().build();
    }

}
