package br.com.restauranthub.api.controller;

import br.com.restauranthub.api.dto.PagamentoRequest;
import br.com.restauranthub.api.dto.PagamentoResponse;
import br.com.restauranthub.application.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PagamentoResponse> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public PagamentoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PagamentoResponse salvar(
            @Valid @RequestBody PagamentoRequest request
    ) {
        return service.salvar(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}