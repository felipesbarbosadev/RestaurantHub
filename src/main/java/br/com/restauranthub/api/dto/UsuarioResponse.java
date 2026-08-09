package br.com.restauranthub.api.dto;

import br.com.restauranthub.domain.enums.Role;

import java.time.LocalDateTime;

public record UsuarioResponse(
    Long id,
    String nome,
    String email,
    Role role,
    Boolean consentimentoLgpd,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}