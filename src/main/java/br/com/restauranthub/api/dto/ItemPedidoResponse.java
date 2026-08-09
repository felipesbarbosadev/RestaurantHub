package br.com.restauranthub.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ItemPedidoResponse(

        Long id,
        String produto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal,
        LocalDateTime createdAt

) {
}