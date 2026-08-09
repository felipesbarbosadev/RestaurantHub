package br.com.restauranthub.api.controller;

import br.com.restauranthub.api.dto.UnidadeRequest;
import br.com.restauranthub.api.dto.UnidadeResponse;
import br.com.restauranthub.application.service.UnidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {

    private final UnidadeService service;

    public UnidadeController(UnidadeService service) {
        this.service = service;
    }

    @GetMapping
    public List<UnidadeResponse> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public UnidadeResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UnidadeResponse salvar(
            @Valid @RequestBody UnidadeRequest request
    ) {
        return service.salvar(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}