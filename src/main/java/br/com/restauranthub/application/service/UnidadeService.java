package br.com.restauranthub.application.service;

import br.com.restauranthub.api.dto.UnidadeRequest;
import br.com.restauranthub.api.dto.UnidadeResponse;
import br.com.restauranthub.api.exception.RecursoNaoEncontradoException;
import br.com.restauranthub.api.exception.RegraNegocioException;
import br.com.restauranthub.domain.entity.Unidade;
import br.com.restauranthub.domain.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeService {

    private final UnidadeRepository repository;

    public UnidadeService(UnidadeRepository repository) {
        this.repository = repository;
    }

    public List<UnidadeResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public UnidadeResponse buscarPorId(Long id) {
        Unidade unidade = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Unidade não encontrada."
                        )
                );

        return converterParaResponse(unidade);
    }

    public UnidadeResponse salvar(UnidadeRequest request) {
        if (repository.existsByNome(request.nome())) {
            throw new RegraNegocioException(
                    "Já existe uma unidade com esse nome."
            );
        }

        Unidade unidade = new Unidade();

        unidade.setNome(request.nome());
        unidade.setEndereco(request.endereco());
        unidade.setTelefone(request.telefone());

        Unidade salva = repository.save(unidade);

        return converterParaResponse(salva);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Unidade não encontrada."
            );
        }

        repository.deleteById(id);
    }

    private UnidadeResponse converterParaResponse(Unidade unidade) {
        return new UnidadeResponse(
                unidade.getId(),
                unidade.getNome(),
                unidade.getEndereco(),
                unidade.getTelefone(),
                unidade.getAtiva(),
                unidade.getCreatedAt(),
                unidade.getUpdatedAt()
        );
    }
}