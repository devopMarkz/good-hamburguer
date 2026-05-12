package com.devopMarkz.good_hamburguer.service;

import com.devopMarkz.good_hamburguer.model.ItemPedido;
import com.devopMarkz.good_hamburguer.model.Pedido;
import com.devopMarkz.good_hamburguer.model.enums.GrupoProduto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@Service
public class PedidoDescontoServiceImpl
        implements PedidoDescontoService {

    @Override
    public BigDecimal calcularDesconto(Pedido pedido) {

        Map<GrupoProduto, Integer> quantidadePorGrupo =
                pedido.getItensPedido()
                        .stream()
                        .collect(groupingBy(
                                item -> item.getProduto().getGrupoProduto(),
                                summingInt(ItemPedido::getQuantidade)
                        ));

        boolean temSanduiche =
                quantidadePorGrupo.getOrDefault(
                        GrupoProduto.SANDUICHE,
                        0
                ) >= 1;

        boolean temBatata =
                quantidadePorGrupo.getOrDefault(
                        GrupoProduto.BATATA,
                        0
                ) >= 1;

        boolean temRefrigerante =
                quantidadePorGrupo.getOrDefault(
                        GrupoProduto.REFRIGERANTE,
                        0
                ) >= 1;

        BigDecimal percentualDesconto = BigDecimal.ZERO;

        if (temSanduiche && temBatata && temRefrigerante) {

            percentualDesconto = new BigDecimal("0.20");

        } else if (temSanduiche && temRefrigerante) {

            percentualDesconto = new BigDecimal("0.15");

        } else if (temSanduiche && temBatata) {

            percentualDesconto = new BigDecimal("0.10");
        }

        return pedido.getSubtotal()
                .multiply(percentualDesconto);
    }
}