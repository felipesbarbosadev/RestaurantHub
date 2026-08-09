package br.com.restauranthub.api.dto;

import br.com.restauranthub.domain.enums.FormaPagamento;
import br.com.restauranthub.domain.enums.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponse(

        Long id,
        Long pedidoId,
        String cliente,
        FormaPagamento formaPagamento,
        StatusPagamento status,
        BigDecimal valor,
        LocalDateTime dataPagamento,
        LocalDateTime createdAt

) {
}