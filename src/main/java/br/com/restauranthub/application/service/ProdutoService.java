package br.com.restauranthub.application.service;

import br.com.restauranthub.api.dto.ProdutoRequest;
import br.com.restauranthub.api.dto.ProdutoResponse;
import br.com.restauranthub.api.exception.RecursoNaoEncontradoException;
import br.com.restauranthub.api.exception.RegraNegocioException;
import br.com.restauranthub.domain.entity.Produto;
import br.com.restauranthub.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<ProdutoResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Produto não encontrado."
                        )
                );

        return converterParaResponse(produto);
    }

    public ProdutoResponse salvar(ProdutoRequest request) {
        if (repository.existsByNome(request.nome())) {
            throw new RegraNegocioException(
                    "Produto já cadastrado."
            );
        }

        Produto produto = new Produto();

        produto.setNome(request.nome());
        produto.setDescricao(request.descricao());
        produto.setCategoria(request.categoria());
        produto.setImagemUrl(request.imagemUrl());

        Produto salvo = repository.save(produto);

        return converterParaResponse(salvo);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Produto não encontrado."
            );
        }

        repository.deleteById(id);
    }

    private ProdutoResponse converterParaResponse(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getImagemUrl(),
                produto.getAtivo(),
                produto.getCreatedAt(),
                produto.getUpdatedAt()
        );
    }
}