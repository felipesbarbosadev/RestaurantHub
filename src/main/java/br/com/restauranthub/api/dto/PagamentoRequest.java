package br.com.restauranthub.api.dto;

import br.com.restauranthub.domain.enums.FormaPagamento;
import br.com.restauranthub.domain.enums.StatusPagamento;
import jakarta.validation.constraints.NotNull;

public record PagamentoRequest(

        @NotNull(message = "O pedido é obrigatório.")
        Long pedidoId,

        @NotNull(message = "A forma de pagamento é obrigatória.")
        FormaPagamento formaPagamento,

        @NotNull(message = "O resultado do pagamento mock é obrigatório.")
        StatusPagamento resultadoMock

) {
}