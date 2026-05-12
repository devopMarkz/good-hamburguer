package com.devopMarkz.good_hamburguer.service;

import com.devopMarkz.good_hamburguer.dto.PedidoRequestDTO;
import com.devopMarkz.good_hamburguer.dto.PedidoResponseDTO;
import com.devopMarkz.good_hamburguer.mapper.PedidoMapper;
import com.devopMarkz.good_hamburguer.model.ItemPedido;
import com.devopMarkz.good_hamburguer.model.Pedido;
import com.devopMarkz.good_hamburguer.model.Produto;
import com.devopMarkz.good_hamburguer.repository.PedidoRepository;
import com.devopMarkz.good_hamburguer.repository.ProdutoRepository;
import com.devopMarkz.good_hamburguer.service.validation.PedidoValidationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoMapper pedidoMapper;
    private final PedidoValidationService pedidoValidationService;
    private final PedidoDescontoService pedidoDescontoService;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ProdutoRepository produtoRepository,
            PedidoMapper pedidoMapper,
            PedidoValidationService pedidoValidationService,
            @Qualifier("pedidoDescontoServiceImpl") PedidoDescontoService pedidoDescontoService
    ) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoMapper = pedidoMapper;
        this.pedidoValidationService = pedidoValidationService;
        this.pedidoDescontoService = pedidoDescontoService;
    }

    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {

        List<ItemPedido> itensDoPedido = dto.getItensPedido()
                .stream()
                .map(itemPedidoRequestDTO -> {

                    Produto produto = produtoRepository
                            .findById(
                                    itemPedidoRequestDTO
                                            .getIdProduto()
                            )
                            .orElse(null);

                    return new ItemPedido(
                            itemPedidoRequestDTO.getQuantidade(),
                            produto
                    );
                }).toList();

        pedidoValidationService.validar(itensDoPedido);

        Pedido pedido = new Pedido(itensDoPedido);

        BigDecimal desconto =
                pedidoDescontoService
                        .calcularDesconto(pedido);

        pedido.aplicarDesconto(desconto);

        pedido = pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedido);
    }
}