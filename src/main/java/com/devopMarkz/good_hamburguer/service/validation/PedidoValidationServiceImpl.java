package com.devopMarkz.good_hamburguer.service.validation;

import com.devopMarkz.good_hamburguer.model.ItemPedido;
import com.devopMarkz.good_hamburguer.model.enums.GrupoProduto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoValidationServiceImpl
        implements PedidoValidationService {

    @Override
    public void validar(List<ItemPedido> itensPedido) {

        int quantidadeSanduiches = itensPedido.stream()
                .filter(item ->
                        item.getProduto().getGrupoProduto()
                                == GrupoProduto.SANDUICHE
                )
                .mapToInt(ItemPedido::getQuantidade)
                .sum();

        int quantidadeBatatas = itensPedido.stream()
                .filter(item ->
                        item.getProduto().getGrupoProduto()
                                == GrupoProduto.BATATA
                )
                .mapToInt(ItemPedido::getQuantidade)
                .sum();

        int quantidadeRefrigerantes = itensPedido.stream()
                .filter(item ->
                        item.getProduto().getGrupoProduto()
                                == GrupoProduto.REFRIGERANTE
                )
                .mapToInt(ItemPedido::getQuantidade)
                .sum();

        if (quantidadeSanduiches > 1) {
            throw new IllegalArgumentException(
                    "Pedido pode conter apenas um sanduíche"
            );
        }

        if (quantidadeBatatas > 1) {
            throw new IllegalArgumentException(
                    "Pedido pode conter apenas uma batata"
            );
        }

        if (quantidadeRefrigerantes > 1) {
            throw new IllegalArgumentException(
                    "Pedido pode conter apenas um refrigerante"
            );
        }
    }
}