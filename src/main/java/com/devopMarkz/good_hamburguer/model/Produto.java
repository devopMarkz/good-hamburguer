package com.devopMarkz.good_hamburguer.model;

import com.devopMarkz.good_hamburguer.model.enums.Categoria;
import com.devopMarkz.good_hamburguer.model.enums.GrupoProduto;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "grupo_produto", nullable = false)
    private GrupoProduto grupoProduto;

    public Produto(String nome, BigDecimal valorUnitario, Categoria categoria, GrupoProduto grupoProduto) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if (valorUnitario == null || valorUnitario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }

        if (grupoProduto == null) {
            throw new IllegalArgumentException("Grupo de produto obrigatório");
        }

        if (categoria == null) {
            throw new IllegalArgumentException("Categoria obrigatória");
        }

        this.nome = nome;
        this.valorUnitario = valorUnitario;
        this.categoria = categoria;
        this.grupoProduto = grupoProduto;
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