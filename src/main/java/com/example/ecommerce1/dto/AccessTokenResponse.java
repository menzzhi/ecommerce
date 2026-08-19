package com.example.ecommerce1.dto;

public record AccessTokenResponse(
        String accessToken,
        long expiresAt,
        String refreshToken
) {
}
