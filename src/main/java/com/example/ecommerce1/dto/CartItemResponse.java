package com.example.ecommerce1.dto;

public record CartItemResponse(
        String nomeProduto,
        Integer quantidade,
        Double precoTotal
) {
}
