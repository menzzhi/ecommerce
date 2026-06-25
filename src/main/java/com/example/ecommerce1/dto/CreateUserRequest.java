package com.example.ecommerce1.dto;

public record CreateUserRequest(
        UserRequest usuario,
        AddressRequest endereco
) {
}
