package com.example.ecommerce1.dto;

public record LoginResponse(
        String accessToken,
        long expiresAt
) {
}
