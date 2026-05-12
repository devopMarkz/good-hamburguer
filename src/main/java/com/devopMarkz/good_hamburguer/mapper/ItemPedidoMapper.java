package com.devopMarkz.good_hamburguer.mapper;

import com.devopMarkz.good_hamburguer.dto.ItemPedidoResponseDTO;
import com.devopMarkz.good_hamburguer.model.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ItemPedidoMapper {

    @Mapping(source = "produto.id", target = "idProduto")
    public abstract ItemPedidoResponseDTO toResponse(ItemPedido itemPedido);

}
