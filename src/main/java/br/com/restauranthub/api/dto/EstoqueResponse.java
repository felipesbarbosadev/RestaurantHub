package br.com.restauranthub.api.dto;

import java.time.LocalDateTime;

public record EstoqueResponse(

        Long id,
        String unidade,
        String produto,
        Integer quantidade,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}