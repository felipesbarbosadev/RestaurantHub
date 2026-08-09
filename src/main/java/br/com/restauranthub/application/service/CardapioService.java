package br.com.restauranthub.application.service;

import br.com.restauranthub.api.dto.CardapioRequest;
import br.com.restauranthub.api.dto.CardapioResponse;
import br.com.restauranthub.api.exception.RecursoNaoEncontradoException;
import br.com.restauranthub.domain.entity.Cardapio;
import br.com.restauranthub.domain.entity.Produto;
import br.com.restauranthub.domain.entity.Unidade;
import br.com.restauranthub.domain.repository.CardapioRepository;
import br.com.restauranthub.domain.repository.ProdutoRepository;
import br.com.restauranthub.domain.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardapioService {

    private final CardapioRepository repository;
    private final ProdutoRepository produtoRepository;
    private final UnidadeRepository unidadeRepository;

    public CardapioService(
            CardapioRepository repository,
            ProdutoRepository produtoRepository,
            UnidadeRepository unidadeRepository
    ) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public List<CardapioResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public CardapioResponse buscarPorId(Long id) {
        Cardapio cardapio = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Item do cardápio não encontrado."
                        )
                );

        return converterParaResponse(cardapio);
    }

    public CardapioResponse salvar(CardapioRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Produto não encontrado."
                        )
                );

        Unidade unidade = unidadeRepository.findById(request.unidadeId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Unidade não encontrada."
                        )
                );

        Cardapio cardapio = new Cardapio();

        cardapio.setProduto(produto);
        cardapio.setUnidade(unidade);
        cardapio.setPreco(request.preco());

        Cardapio salvo = repository.save(cardapio);

        return converterParaResponse(salvo);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Item do cardápio não encontrado."
            );
        }

        repository.deleteById(id);
    }

    private CardapioResponse converterParaResponse(Cardapio cardapio) {
        return new CardapioResponse(
                cardapio.getId(),
                cardapio.getUnidade().getNome(),
                cardapio.getProduto().getNome(),
                cardapio.getPreco(),
                cardapio.getDisponivel(),
                cardapio.getCreatedAt(),
                cardapio.getUpdatedAt()
        );
    }
}