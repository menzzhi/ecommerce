package com.example.ecommerce1.controller;

import com.example.ecommerce1.dto.OrderResponse;
import com.example.ecommerce1.service.OrderService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<OrderResponse> finalizarPedido(@RequestParam Long userId){
        OrderResponse orderResponse = orderService.finishOrder(userId);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrdersUser(@RequestParam Long userId){
        List<OrderResponse> all = orderService.getAll(userId);
        return ResponseEntity.ok(all);
    }
}
