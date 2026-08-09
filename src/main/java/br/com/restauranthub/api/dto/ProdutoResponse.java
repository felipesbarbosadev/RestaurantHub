package br.com.restauranthub.api.dto;

import java.time.LocalDateTime;

public record ProdutoResponse(

        Long id,
        String nome,
        String descricao,
        String categoria,
        String imagemUrl,
        Boolean ativo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}