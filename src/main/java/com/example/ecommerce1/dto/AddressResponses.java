package com.example.ecommerce1.dto;

public record AddressResponses(
        String logradouro,
        String estado,
        String cidade,
        String cep
) {
}
