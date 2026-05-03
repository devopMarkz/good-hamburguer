package com.devopMarkz.good_hamburguer.model;

import com.devopMarkz.good_hamburguer.model.enums.Categoria;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_produto")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "valor_unitario", nullable = false)
    private BigDecimal valorUnitario;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private Categoria categoria;

    public Produto(String nome, BigDecimal valorUnitario, Categoria categoria) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if (valorUnitario == null || valorUnitario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }

        if (categoria == null) {
            throw new IllegalArgumentException("Categoria obrigatória");
        }

        this.nome = nome;
        this.valorUnitario = valorUnitario;
        this.categoria = categoria;
    }

    public void alterarPreco(BigDecimal novoValor) {
        if (novoValor == null || novoValor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }
        this.valorUnitario = novoValor;
    }

    public void alterarCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria obrigatória");
        }
        this.categoria = categoria;
    }
}