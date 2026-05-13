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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        List<ItemPedido> itensDoPedido = criarItens(dto);

        pedidoValidationService.validar(itensDoPedido);

        Pedido pedido = new Pedido(itensDoPedido);

        aplicarDesconto(pedido);

        pedido = pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedido);
    }

    @Transactional(readOnly = true)
    public Page<PedidoResponseDTO> listarPedidos(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Pedido> pedidos = pedidoRepository.findAll(pageable);
        return pedidos.map(pedidoMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        return pedidoMapper.toResponse(pedido);
    }

    @Transactional
    public void deletarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pedido não encontrado")
                );

        pedidoRepository.delete(pedido);
    }

    @Transactional
    public PedidoResponseDTO atualizarPedido(Long id, PedidoRequestDTO dto) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pedido não encontrado")
                );

        pedido.getItensPedido()
                .stream()
                .toList()
                .forEach(pedido::removerItemPedido);

        List<ItemPedido> novosItens = criarItens(dto);

        pedidoValidationService.validar(novosItens);

        novosItens.forEach(pedido::adicionarItemPedido);

        aplicarDesconto(pedido);

        pedido = pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedido);
    }

    private List<ItemPedido> criarItens(PedidoRequestDTO dto) {

        return dto.getItensPedido()
                .stream()
                .map(itemPedidoRequestDTO -> {

                    Produto produto = produtoRepository
                            .findById(itemPedidoRequestDTO.getIdProduto())
                            .orElseThrow(() ->
                                    new EntityNotFoundException(
                                            "Produto não encontrado"
                                    )
                            );

                    return new ItemPedido(
                            itemPedidoRequestDTO.getQuantidade(),
                            produto
                    );
                })
                .toList();
    }

    private void aplicarDesconto(Pedido pedido) {
        BigDecimal desconto =
                pedidoDescontoService.calcularDesconto(pedido);

        pedido.aplicarDesconto(desconto);
    }

}