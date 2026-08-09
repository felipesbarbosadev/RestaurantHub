package br.com.restauranthub.api.controller;

import br.com.restauranthub.api.dto.ItemPedidoRequest;
import br.com.restauranthub.api.dto.ItemPedidoResponse;
import br.com.restauranthub.application.service.ItemPedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-pedido")
public class ItemPedidoController {

    private final ItemPedidoService service;

    public ItemPedidoController(ItemPedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ItemPedidoResponse> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ItemPedidoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemPedidoResponse salvar(
            @Valid @RequestBody ItemPedidoRequest request
    ) {
        return service.salvar(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}