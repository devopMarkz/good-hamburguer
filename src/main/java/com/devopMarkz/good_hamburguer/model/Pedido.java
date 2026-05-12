package com.devopMarkz.good_hamburguer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "tb_pedido")
@Getter
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(name = "desconto", nullable = false)
    private BigDecimal desconto = BigDecimal.ZERO;

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(
            name = "data_pedido",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private LocalDateTime dataPedido;

    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemPedido> itensPedido = new ArrayList<>();

    public Pedido(List<ItemPedido> itensPedido) {
        if (itensPedido != null) {
            itensPedido.forEach(this::adicionarItemPedido);
        }
    }

    public List<ItemPedido> getItensPedido() {
        return Collections.unmodifiableList(itensPedido);
    }

    public void adicionarItemPedido(ItemPedido itemPedido) {

        if (itemPedido == null) {
            throw new IllegalArgumentException(
                    "ItemPedido não pode ser nulo"
            );
        }

        if (itemPedido.getPedido() != null) {
            throw new IllegalStateException(
                    "Item já pertence a outro pedido"
            );
        }

        Optional<ItemPedido> existente = this.itensPedido.stream()
                .filter(i ->
                        i.getProduto().equals(
                                itemPedido.getProduto()
                        )
                )
                .findFirst();

        if (existente.isPresent()) {

            ItemPedido item = existente.get();

            item.alterarQuantidade(
                    item.getQuantidade()
                            + itemPedido.getQuantidade()
            );

            return;
        }

        itemPedido.setPedido(this);

        this.itensPedido.add(itemPedido);

        this.subtotal = this.subtotal.add(
                itemPedido.getSubtotal()
        );

        recalcularTotal();
    }

    public void removerItemPedido(ItemPedido itemPedido) {

        if (itemPedido == null
                || itemPedido.getPedido() != this) {

            throw new IllegalStateException(
                    "Item não pertence a este pedido"
            );
        }

        this.itensPedido.remove(itemPedido);

        itemPedido.setPedido(null);

        this.subtotal = this.subtotal.subtract(
                itemPedido.getSubtotal()
        );

        recalcularTotal();
    }

    public void atualizarSubtotal(
            BigDecimal subtotalAntigo,
            BigDecimal subtotalNovo
    ) {

        this.subtotal = this.subtotal
                .subtract(subtotalAntigo)
                .add(subtotalNovo);

        recalcularTotal();
    }

    public void aplicarDesconto(BigDecimal desconto) {

        if (desconto == null
                || desconto.compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Desconto inválido"
            );
        }

        this.desconto = desconto;

        recalcularTotal();
    }

    private void recalcularTotal() {

        this.valorTotal = this.subtotal.subtract(
                this.desconto
        );
    }
}