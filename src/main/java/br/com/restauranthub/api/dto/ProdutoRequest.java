package br.com.restauranthub.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProdutoRequest(

        @NotBlank(message = "O nome do produto é obrigatório.")
        @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres.")
        String nome,

        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
        String descricao,

        @NotBlank(message = "A categoria é obrigatória.")
        @Size(max = 50, message = "A categoria deve ter no máximo 50 caracteres.")
        String categoria,

        @Size(max = 255, message = "A URL da imagem deve ter no máximo 255 caracteres.")
        String imagemUrl

) {
}