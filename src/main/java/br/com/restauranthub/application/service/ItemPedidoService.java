package br.com.restauranthub.application.service;

import br.com.restauranthub.api.dto.ItemPedidoRequest;
import br.com.restauranthub.api.dto.ItemPedidoResponse;
import br.com.restauranthub.api.exception.RecursoNaoEncontradoException;
import br.com.restauranthub.domain.entity.Cardapio;
import br.com.restauranthub.domain.entity.ItemPedido;
import br.com.restauranthub.domain.entity.Pedido;
import br.com.restauranthub.domain.repository.CardapioRepository;
import br.com.restauranthub.domain.repository.ItemPedidoRepository;
import br.com.restauranthub.domain.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository repository;
    private final PedidoRepository pedidoRepository;
    private final CardapioRepository cardapioRepository;

    public ItemPedidoService(
            ItemPedidoRepository repository,
            PedidoRepository pedidoRepository,
            CardapioRepository cardapioRepository
    ) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
        this.cardapioRepository = cardapioRepository;
    }

    public List<ItemPedidoResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public ItemPedidoResponse buscarPorId(Long id) {
        ItemPedido item = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Item do pedido não encontrado."
                        )
                );

        return converterParaResponse(item);
    }

    public ItemPedidoResponse salvar(ItemPedidoRequest request) {
        Pedido pedido = pedidoRepository.findById(request.pedidoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Pedido não encontrado."
                        )
                );

        Cardapio cardapio = cardapioRepository.findById(request.cardapioId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Item do cardápio não encontrado."
                        )
                );

        BigDecimal preco = cardapio.getPreco();

        BigDecimal subtotal = preco.multiply(
                BigDecimal.valueOf(request.quantidade())
        );

        ItemPedido item = new ItemPedido();

        item.setPedido(pedido);
        item.setCardapio(cardapio);
        item.setQuantidade(request.quantidade());
        item.setPrecoUnitario(preco);
        item.setSubtotal(subtotal);

        ItemPedido salvo = repository.save(item);

        return converterParaResponse(salvo);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Item do pedido não encontrado."
            );
        }

        repository.deleteById(id);
    }

    private ItemPedidoResponse converterParaResponse(ItemPedido item) {
        return new ItemPedidoResponse(
                item.getId(),
                item.getCardapio().getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getSubtotal(),
                item.getCreatedAt()
        );
    }
}