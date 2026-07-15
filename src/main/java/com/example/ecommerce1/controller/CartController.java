package com.example.ecommerce1.controller;

import com.example.ecommerce1.dto.CartResponse;
import com.example.ecommerce1.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createCart(@RequestParam Long userId,
                                           @RequestParam Long productId,
                                           @RequestParam Integer quantity){
        cartService.createCart(userId, productId, quantity);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CartResponse> getUserCartById(@RequestParam Long userId){
        CartResponse cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CartResponse>> getAllCarts(){
        List<CartResponse> allCarts = cartService.getAllCarts();
        return ResponseEntity.ok(allCarts);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCart(@RequestParam Long userId,
                                           @RequestParam Long productId,
                                           @RequestParam Integer quantity){
        cartService.updateCart(userId, productId, quantity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCart(@RequestParam Long userId){
        cartService.deleteCart(userId);
        return ResponseEntity.noContent().build();
    }
}
