package com.example.ecommerce1.dto;

import java.math.BigDecimal;

public record ProductRequest(
        Long categoriaId,
        String nome,
        String descricao,
        Double preco,
        int quantidadeEstoque,
        boolean ativo
) {
}
