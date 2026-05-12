package com.devopMarkz.good_hamburguer.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PedidoRequestDTO {

    private List<ItemPedidoRequestDTO> itensPedido = new ArrayList<>();

}
