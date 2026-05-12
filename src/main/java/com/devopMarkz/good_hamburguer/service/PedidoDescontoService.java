package com.devopMarkz.good_hamburguer.service;

import com.devopMarkz.good_hamburguer.model.Pedido;

import java.math.BigDecimal;

public interface PedidoDescontoService {

    BigDecimal calcularDesconto(Pedido pedido);

}
