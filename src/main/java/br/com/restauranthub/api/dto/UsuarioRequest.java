package br.com.restauranthub.api.dto;

import br.com.restauranthub.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres.")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Informe um e-mail válido.")
        @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 100,
                message = "A senha deve ter entre 6 e 100 caracteres.")
        String senha,

        @NotNull(message = "O perfil do usuário é obrigatório.")
        Role role,

        @NotNull(message = "O consentimento LGPD deve ser informado.")
        Boolean consentimentoLgpd

) {
}