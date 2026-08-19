package com.example.ecommerce1.dto;

public record RefreshTokenResponse(
        String refreshToken,
        Long refreshTokenExpiresAt,
        String accessToken
) {
}
