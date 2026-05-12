package com.devopMarkz.good_hamburguer.dto;

import java.math.BigDecimal;

public record ProdutoResponseDTO (
        Long id,
        String nome,
        BigDecimal valorUnitario,
        String categoria,
        String grupoProduto
){}
