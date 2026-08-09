package br.com.restauranthub.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UnidadeRequest(

        @NotBlank(message = "O nome da unidade é obrigatório.")
        @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres.")
        String nome,

        @NotBlank(message = "O endereço é obrigatório.")
        @Size(max = 255, message = "O endereço deve ter no máximo 255 caracteres.")
        String endereco,

        @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres.")
        String telefone

) {
}