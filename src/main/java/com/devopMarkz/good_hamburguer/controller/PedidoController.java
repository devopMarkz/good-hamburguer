package com.devopMarkz.good_hamburguer.controller;

import com.devopMarkz.good_hamburguer.dto.PedidoRequestDTO;
import com.devopMarkz.good_hamburguer.dto.PedidoResponseDTO;
import com.devopMarkz.good_hamburguer.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Page<PedidoResponseDTO>> listarPedidos(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(pedidoService.listarPedidos(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        pedidoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizarPedido(
            @PathVariable Long id,
            @Valid @RequestBody PedidoRequestDTO dto
    ) {

        PedidoResponseDTO responseDTO =
                pedidoService.atualizarPedido(id, dto);

        return ResponseEntity.ok(responseDTO);
    }

}
