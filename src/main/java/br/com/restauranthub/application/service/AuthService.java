package br.com.restauranthub.application.service;

import br.com.restauranthub.api.dto.LoginRequest;
import br.com.restauranthub.api.dto.LoginResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final long expirationSeconds;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder,
            @Value("${security.jwt.expiration-seconds}")
            long expirationSeconds
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.expirationSeconds = expirationSeconds;
    }

    public LoginResponse autenticar(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.senha()
                        )
                );

        Instant agora = Instant.now();
        Instant expiracao = agora.plusSeconds(expirationSeconds);

        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority ->
                        authority.substring("ROLE_".length())
                )
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("restauranthub")
                .issuedAt(agora)
                .expiresAt(expiracao)
                .subject(authentication.getName())
                .claim("roles", roles)
                .build();

        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .type("JWT")
                .build();

        String token = jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();

        return new LoginResponse(
                token,
                "Bearer",
                expirationSeconds
        );
    }
}