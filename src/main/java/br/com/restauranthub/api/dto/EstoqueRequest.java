package br.com.restauranthub.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EstoqueRequest(

        @NotNull(message = "A unidade é obrigatória.")
        Long unidadeId,

        @NotNull(message = "O produto é obrigatório.")
        Long produtoId,

        @NotNull(message = "A quantidade é obrigatória.")
        @Min(value = 0, message = "A quantidade não pode ser negativa.")
        Integer quantidade

) {
}