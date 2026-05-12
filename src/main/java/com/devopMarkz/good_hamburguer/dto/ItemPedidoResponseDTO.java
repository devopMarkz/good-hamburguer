package com.devopMarkz.good_hamburguer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoResponseDTO {

    private Long id;
    private Long idProduto;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal subtotal;

}
