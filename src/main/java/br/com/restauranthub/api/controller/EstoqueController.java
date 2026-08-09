package br.com.restauranthub.api.controller;

import br.com.restauranthub.api.dto.EstoqueRequest;
import br.com.restauranthub.api.dto.EstoqueResponse;
import br.com.restauranthub.application.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    private final EstoqueService service;

    public EstoqueController(EstoqueService service) {
        this.service = service;
    }

    @GetMapping
    public List<EstoqueResponse> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public EstoqueResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstoqueResponse salvar(
            @Valid @RequestBody EstoqueRequest request
    ) {
        return service.salvar(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}