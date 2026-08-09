package br.com.restauranthub.api.controller;

import br.com.restauranthub.api.dto.PedidoRequest;
import br.com.restauranthub.api.dto.PedidoResponse;
import br.com.restauranthub.api.dto.StatusPedidoRequest;
import br.com.restauranthub.application.service.PedidoService;
import br.com.restauranthub.domain.enums.CanalPedido;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PedidoResponse> listarTodos(
            @RequestParam(required = false)
            CanalPedido canalPedido
    ) {
        return service.listarTodos(canalPedido);
    }

    @GetMapping("/{id}")
    public PedidoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse salvar(
            @Valid @RequestBody PedidoRequest request
    ) {
        return service.salvar(request);
    }

    @PatchMapping("/{id}/status")
    public PedidoResponse atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusPedidoRequest request
    ) {
        return service.atualizarStatus(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}