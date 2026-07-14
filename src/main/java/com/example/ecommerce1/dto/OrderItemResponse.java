package com.example.ecommerce1.dto;

public record OrderItemResponse(
        String nomeProduto,
        Integer quantidade,
        Double precoTotal
) {
}
