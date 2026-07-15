package com.example.ecommerce1.dto;

import java.util.List;

public record CartResponse(
        String nome,
        List<CartItemResponse> cartItemResponseList,
        Double precoTotalCarrinho
) {
}
