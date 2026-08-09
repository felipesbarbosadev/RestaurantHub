package br.com.restauranthub.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequest(

        @NotNull(message = "O pedido é obrigatório.")
        Long pedidoId,

        @NotNull(message = "O item do cardápio é obrigatório.")
        Long cardapioId,

        @NotNull(message = "A quantidade é obrigatória.")
        @Min(value = 1, message = "A quantidade deve ser maior que zero.")
        Integer quantidade

) {
}