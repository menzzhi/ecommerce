package com.example.ecommerce1.controller;

import com.example.ecommerce1.dto.OrderResponse;
import com.example.ecommerce1.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/finalizarPedido")
    public ResponseEntity<OrderResponse> takeOrder(JwtAuthenticationToken token){
        OrderResponse orderResponse = orderService.finishOrder(token);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllMyOrders(JwtAuthenticationToken token){
        List<OrderResponse> all = orderService.getAll(token);
        return ResponseEntity.ok(all);
    }
}
