package br.com.restauranthub.application.service;

import br.com.restauranthub.api.dto.EstoqueRequest;
import br.com.restauranthub.api.dto.EstoqueResponse;
import br.com.restauranthub.api.exception.RecursoNaoEncontradoException;
import br.com.restauranthub.domain.entity.Estoque;
import br.com.restauranthub.domain.entity.Produto;
import br.com.restauranthub.domain.entity.Unidade;
import br.com.restauranthub.domain.repository.EstoqueRepository;
import br.com.restauranthub.domain.repository.ProdutoRepository;
import br.com.restauranthub.domain.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    private final EstoqueRepository repository;
    private final ProdutoRepository produtoRepository;
    private final UnidadeRepository unidadeRepository;

    public EstoqueService(
            EstoqueRepository repository,
            ProdutoRepository produtoRepository,
            UnidadeRepository unidadeRepository
    ) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public List<EstoqueResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public EstoqueResponse buscarPorId(Long id) {
        Estoque estoque = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Estoque não encontrado."
                        )
                );

        return converterParaResponse(estoque);
    }

    public EstoqueResponse salvar(EstoqueRequest request) {
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

        Estoque estoque = new Estoque();

        estoque.setProduto(produto);
        estoque.setUnidade(unidade);
        estoque.setQuantidade(request.quantidade());

        Estoque salvo = repository.save(estoque);

        return converterParaResponse(salvo);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Estoque não encontrado."
            );
        }

        repository.deleteById(id);
    }

    private EstoqueResponse converterParaResponse(Estoque estoque) {
        return new EstoqueResponse(
                estoque.getId(),
                estoque.getUnidade().getNome(),
                estoque.getProduto().getNome(),
                estoque.getQuantidade(),
                estoque.getCreatedAt(),
                estoque.getUpdatedAt()
        );
    }
}