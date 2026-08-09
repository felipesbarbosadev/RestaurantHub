package br.com.restauranthub.api.controller;

import br.com.restauranthub.api.dto.LoginRequest;
import br.com.restauranthub.api.dto.LoginResponse;
import br.com.restauranthub.application.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return service.autenticar(request);
    }

}