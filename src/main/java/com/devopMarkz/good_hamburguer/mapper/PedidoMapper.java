package com.devopMarkz.good_hamburguer.mapper;

import com.devopMarkz.good_hamburguer.dto.PedidoResponseDTO;
import com.devopMarkz.good_hamburguer.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ItemPedidoMapper.class})
public abstract class PedidoMapper {

    public abstract PedidoResponseDTO toResponse(Pedido pedido);

}
