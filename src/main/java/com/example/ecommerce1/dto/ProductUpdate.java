package com.example.ecommerce1.dto;

public record ProductUpdate(
        String nome,
        String descricao,
        Double preco,
        Integer quantidadeEstoque
) {
}
