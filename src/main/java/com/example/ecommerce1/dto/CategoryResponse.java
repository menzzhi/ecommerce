package com.example.ecommerce1.dto;

import java.util.List;

public record CategoryResponse(
        String categoria,
        List<ProductResponse> produtos
) {
}
