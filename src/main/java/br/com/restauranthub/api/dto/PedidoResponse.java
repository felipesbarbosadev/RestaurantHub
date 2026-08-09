package br.com.restauranthub.api.dto;

import br.com.restauranthub.domain.enums.CanalPedido;
import br.com.restauranthub.domain.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponse(

        Long id,
        String cliente,
        String unidade,
        CanalPedido canalPedido,
        StatusPedido status,
        BigDecimal valorTotal,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}