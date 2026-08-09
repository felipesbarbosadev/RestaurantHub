package br.com.restauranthub.application.service;

import br.com.restauranthub.api.dto.PagamentoRequest;
import br.com.restauranthub.api.dto.PagamentoResponse;
import br.com.restauranthub.api.exception.RecursoNaoEncontradoException;
import br.com.restauranthub.api.exception.RegraNegocioException;
import br.com.restauranthub.domain.entity.Pagamento;
import br.com.restauranthub.domain.entity.Pedido;
import br.com.restauranthub.domain.enums.StatusPagamento;
import br.com.restauranthub.domain.repository.PagamentoRepository;
import br.com.restauranthub.domain.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;
    private final PedidoRepository pedidoRepository;

    public PagamentoService(
            PagamentoRepository repository,
            PedidoRepository pedidoRepository
    ) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<PagamentoResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public PagamentoResponse buscarPorId(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Pagamento não encontrado."
                        )
                );

        return converterParaResponse(pagamento);
    }

    public PagamentoResponse salvar(PagamentoRequest request) {

        if (repository.existsByPedidoId(request.pedidoId())) {
            throw new RegraNegocioException(
                    "Este pedido já possui um pagamento."
            );
        }

        if (request.resultadoMock() != StatusPagamento.APROVADO
                && request.resultadoMock() != StatusPagamento.RECUSADO) {

            throw new RegraNegocioException(
                    "O pagamento mock deve ser APROVADO ou RECUSADO."
            );
        }

        Pedido pedido = pedidoRepository.findById(request.pedidoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Pedido não encontrado."
                        )
                );

        Pagamento pagamento = new Pagamento();

        pagamento.setPedido(pedido);
        pagamento.setFormaPagamento(request.formaPagamento());
        pagamento.setStatus(request.resultadoMock());
        pagamento.setValor(pedido.getValorTotal());
        pagamento.setDataPagamento(LocalDateTime.now());

        Pagamento salvo = repository.save(pagamento);

        return converterParaResponse(salvo);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Pagamento não encontrado."
            );
        }

        repository.deleteById(id);
    }

    private PagamentoResponse converterParaResponse(Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getId(),
                pagamento.getPedido().getId(),
                pagamento.getPedido().getUsuario().getNome(),
                pagamento.getFormaPagamento(),
                pagamento.getStatus(),
                pagamento.getValor(),
                pagamento.getDataPagamento(),
                pagamento.getCreatedAt()
        );
    }
}