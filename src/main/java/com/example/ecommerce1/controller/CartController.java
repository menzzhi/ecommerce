package com.example.ecommerce1.controller;

import com.example.ecommerce1.dto.CartResponse;
import com.example.ecommerce1.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
    public ResponseEntity<Void> createCart(JwtAuthenticationToken token,
                                           @RequestParam Long productId,
                                           @RequestParam Integer quantity){
        cartService.createCart(token, productId, quantity);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CartResponse> getMyCart(JwtAuthenticationToken token){
        CartResponse cart = cartService.getCart(token);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<CartResponse>> getAllCarts(){
        List<CartResponse> allCarts = cartService.getAllCarts();
        return ResponseEntity.ok(allCarts);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCart(JwtAuthenticationToken token,
                                           @RequestParam Long productId,
                                           @RequestParam Integer quantity){
        cartService.updateCart(token, productId, quantity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCart(JwtAuthenticationToken token){
        cartService.deleteCart(token);
        return ResponseEntity.noContent().build();
    }
}
