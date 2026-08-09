package br.com.restauranthub.domain.repository;

import br.com.restauranthub.domain.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    boolean existsByPedidoId(Long pedidoId);

}