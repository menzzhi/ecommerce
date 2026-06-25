package com.example.ecommerce1.dto;

public record AddressRequest(
        String logradouro,
        String cidade,
        String cep,
        String estado
) {
}
