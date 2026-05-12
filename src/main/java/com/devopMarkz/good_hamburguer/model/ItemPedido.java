package com.devopMarkz.good_hamburguer.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_item_pedido")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(nullable = false, updatable = false)
    private BigDecimal valorUnitario;

    @Setter(AccessLevel.NONE)
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    public ItemPedido(Integer quantidade, Produto produto) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado");
        }

        this.quantidade = quantidade;
        this.produto = produto;
        this.valorUnitario = produto.getValorUnitario();
        this.subtotal = calcularSubtotal();
    }

    void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void alterarQuantidade(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        BigDecimal subtotalAntigo = this.subtotal;

        this.quantidade = quantidade;
        this.subtotal = calcularSubtotal();

        if (this.pedido != null) {
            this.pedido.atualizarSubtotal(subtotalAntigo, this.subtotal);
        }
    }

    private BigDecimal calcularSubtotal(){
        return this.valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}