package br.com.restauranthub.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CardapioResponse(

        Long id,
        String unidade,
        String produto,
        BigDecimal preco,
        Boolean disponivel,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}