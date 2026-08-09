package br.com.restauranthub.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CardapioRequest(

        @NotNull(message = "A unidade é obrigatória.")
        Long unidadeId,

        @NotNull(message = "O produto é obrigatório.")
        Long produtoId,

        @NotNull(message = "O preço é obrigatório.")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
        BigDecimal preco

) {
}