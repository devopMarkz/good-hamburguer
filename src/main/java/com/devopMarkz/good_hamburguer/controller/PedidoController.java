package com.devopMarkz.good_hamburguer.controller;

import com.devopMarkz.good_hamburguer.dto.PedidoRequestDTO;
import com.devopMarkz.good_hamburguer.dto.PedidoResponseDTO;
import com.devopMarkz.good_hamburguer.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.devopMarkz.good_hamburguer.utils.UriGenerator.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO responseDTO = pedidoService.criarPedido(dto);
        URI uri = gerarUri(responseDTO.getId());
        return ResponseEntity.created(uri).body(responseDTO);
    }

}
