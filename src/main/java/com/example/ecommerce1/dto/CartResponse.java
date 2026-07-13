package com.example.ecommerce1.dto;

import java.util.List;

public record CartResponse(
        List<CartItemResponse> cartItemResponseList,
        Double precoTotalCarrinho
) {
}
