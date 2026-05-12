package com.devopMarkz.good_hamburguer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedidoRequestDTO {

    @NotNull(message = "Id do produto precisa ser informado.")
    @EqualsAndHashCode.Include
    Long idProduto;

    @NotNull(message = "Quantidade precisa ser informada.")
    @Positive(message = "Quantidade precisa ser maior que 0.")
    Integer quantidade;

}
