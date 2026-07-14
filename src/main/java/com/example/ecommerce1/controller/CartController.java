package com.example.ecommerce1.controller;

import com.example.ecommerce1.dto.CartResponse;
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

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getUserCartById(@PathVariable Long userId){
        CartResponse cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCart(@RequestParam Long userId,
                                           @RequestParam Long productId,
                                           @RequestParam Integer quantity){
        cartService.updateCart(userId, productId, quantity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long userId){
        cartService.deleteCart(userId);
        return ResponseEntity.noContent().build();
    }
}
