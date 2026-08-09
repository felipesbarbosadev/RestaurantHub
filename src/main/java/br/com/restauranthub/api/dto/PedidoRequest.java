package br.com.restauranthub.api.dto;

import br.com.restauranthub.domain.enums.CanalPedido;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PedidoRequest(

        @NotNull(message = "O usuário é obrigatório.")
        Long usuarioId,

        @NotNull(message = "A unidade é obrigatória.")
        Long unidadeId,

        @NotNull(message = "O canal do pedido é obrigatório.")
        CanalPedido canalPedido,

        @NotNull(message = "O valor total é obrigatório.")
        @DecimalMin(
                value = "0.00",
                message = "O valor total não pode ser negativo."
        )
        BigDecimal valorTotal

) {
}