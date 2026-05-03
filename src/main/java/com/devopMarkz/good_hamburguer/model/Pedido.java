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

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(name = "data_pedido", nullable = false, insertable = false, updatable = false)
    private LocalDateTime dataPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itensPedido = new ArrayList<>();

    public Pedido(List<ItemPedido> itensPedido){
        if (itensPedido != null) {
            itensPedido.forEach(this::adicionarItemPedido);
        }
    }

    public List<ItemPedido> getItensPedido() {
        return Collections.unmodifiableList(itensPedido);
    }

    public void adicionarItemPedido(ItemPedido itemPedido) {
        if (itemPedido == null) {
            throw new IllegalArgumentException("ItemPedido não pode ser nulo");
        }

        if (itemPedido.getPedido() != null) {
            throw new IllegalStateException("Item já pertence a outro pedido");
        }

        Optional<ItemPedido> existente = this.itensPedido.stream()
                .filter(i -> i.getProduto().equals(itemPedido.getProduto()))
                .findFirst();

        if (existente.isPresent()) {
            ItemPedido item = existente.get();
            item.alterarQuantidade(item.getQuantidade() + itemPedido.getQuantidade());
            return;
        }

        itemPedido.setPedido(this);
        this.itensPedido.add(itemPedido);
        this.valorTotal = this.valorTotal.add(itemPedido.getSubtotal());
    }

    public void removerItemPedido(ItemPedido itemPedido) {
        if (itemPedido == null || itemPedido.getPedido() != this) {
            throw new IllegalStateException("Item não pertence a este pedido");
        }

        this.itensPedido.remove(itemPedido);
        itemPedido.setPedido(null);
        this.valorTotal = this.valorTotal.subtract(itemPedido.getSubtotal());
    }

    public void atualizarTotal(BigDecimal antigo, BigDecimal novo) {
        this.valorTotal = this.valorTotal
                .subtract(antigo)
                .add(novo);
    }
}