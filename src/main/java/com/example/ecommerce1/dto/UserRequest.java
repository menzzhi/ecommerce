package com.example.ecommerce1.dto;

public record UserRequest(
        String nome,
        String email,
        String senha
) {
}
