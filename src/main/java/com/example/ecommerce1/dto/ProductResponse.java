package com.example.ecommerce1.dto;

public record ProductResponse(
        String nome,
        String descricao,
        double preco,
        int quantidadeEstoque
) {
}
