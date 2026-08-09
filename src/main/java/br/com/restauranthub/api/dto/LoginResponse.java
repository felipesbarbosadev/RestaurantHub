package br.com.restauranthub.api.dto;

public record LoginResponse(

        String accessToken,
        String tokenType,
        long expiresIn

) {
}