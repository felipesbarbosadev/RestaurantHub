package br.com.restauranthub.application.service;

import br.com.restauranthub.api.dto.PedidoRequest;
import br.com.restauranthub.api.dto.PedidoResponse;
import br.com.restauranthub.api.dto.StatusPedidoRequest;
import br.com.restauranthub.api.exception.RecursoNaoEncontradoException;
import br.com.restauranthub.domain.entity.Pedido;
import br.com.restauranthub.domain.entity.Unidade;
import br.com.restauranthub.domain.entity.Usuario;
import br.com.restauranthub.domain.enums.CanalPedido;
import br.com.restauranthub.domain.repository.PedidoRepository;
import br.com.restauranthub.domain.repository.UnidadeRepository;
import br.com.restauranthub.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final UnidadeRepository unidadeRepository;

    public PedidoService(
            PedidoRepository repository,
            UsuarioRepository usuarioRepository,
            UnidadeRepository unidadeRepository
    ) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public List<PedidoResponse> listarTodos(CanalPedido canalPedido) {
        List<Pedido> pedidos;

        if (canalPedido == null) {
            pedidos = repository.findAll();
        } else {
            pedidos = repository.findByCanalPedido(canalPedido);
        }

        return pedidos.stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Pedido não encontrado."
                        )
                );

        return converterParaResponse(pedido);
    }

    public PedidoResponse salvar(PedidoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Usuário não encontrado."
                        )
                );

        Unidade unidade = unidadeRepository.findById(request.unidadeId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Unidade não encontrada."
                        )
                );

        Pedido pedido = new Pedido();

        pedido.setUsuario(usuario);
        pedido.setUnidade(unidade);
        pedido.setCanalPedido(request.canalPedido());
        pedido.setValorTotal(request.valorTotal());

        Pedido salvo = repository.save(pedido);

        return converterParaResponse(salvo);
    }

    public PedidoResponse atualizarStatus(
            Long id,
            StatusPedidoRequest request
    ) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Pedido não encontrado."
                        )
                );

        pedido.setStatus(request.status());

        Pedido atualizado = repository.save(pedido);

        return converterParaResponse(atualizado);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Pedido não encontrado."
            );
        }

        repository.deleteById(id);
    }

    private PedidoResponse converterParaResponse(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getUsuario().getNome(),
                pedido.getUnidade().getNome(),
                pedido.getCanalPedido(),
                pedido.getStatus(),
                pedido.getValorTotal(),
                pedido.getCreatedAt(),
                pedido.getUpdatedAt()
        );
    }
}