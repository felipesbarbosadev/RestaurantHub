package br.com.restauranthub.api.dto;

import java.time.LocalDateTime;

public record UnidadeResponse(

        Long id,
        String nome,
        String endereco,
        String telefone,
        Boolean ativa,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}