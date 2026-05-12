package com.devopMarkz.good_hamburguer.service.validation;

import com.devopMarkz.good_hamburguer.model.ItemPedido;

import java.util.List;

public interface PedidoValidationService {

    void validar(List<ItemPedido> itensPedido);

}