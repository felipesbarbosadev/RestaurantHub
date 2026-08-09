package br.com.restauranthub.api.dto;

import br.com.restauranthub.domain.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;

public record StatusPedidoRequest(

        @NotNull(message = "O status é obrigatório.")
        StatusPedido status

) {
}