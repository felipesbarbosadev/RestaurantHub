package br.com.restauranthub.domain.repository;

import br.com.restauranthub.domain.entity.Pedido;
import br.com.restauranthub.domain.enums.CanalPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByCanalPedido(CanalPedido canalPedido);
}