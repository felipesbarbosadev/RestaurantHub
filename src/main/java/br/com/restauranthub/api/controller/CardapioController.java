package br.com.restauranthub.api.controller;

import br.com.restauranthub.api.dto.CardapioRequest;
import br.com.restauranthub.api.dto.CardapioResponse;
import br.com.restauranthub.application.service.CardapioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapios")
public class CardapioController {

    private final CardapioService service;

    public CardapioController(CardapioService service) {
        this.service = service;
    }

    @GetMapping
    public List<CardapioResponse> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public CardapioResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardapioResponse salvar(
            @Valid @RequestBody CardapioRequest request
    ) {
        return service.salvar(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}