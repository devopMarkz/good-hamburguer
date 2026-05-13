package com.devopMarkz.good_hamburguer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal desconto;
    private BigDecimal valorTotal;
    private String dataPedido;
    private List<ItemPedidoResponseDTO> itensPedido = new ArrayList<>();

}