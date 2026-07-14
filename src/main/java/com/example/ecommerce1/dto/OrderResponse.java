package com.example.ecommerce1.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        String status,
        Double valorTotal,
        Double frete,
        LocalDateTime realizadoEm,
        List<OrderItemResponse> orderItem
) {
}
