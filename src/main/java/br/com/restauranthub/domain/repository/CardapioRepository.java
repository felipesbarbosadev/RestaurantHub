package br.com.restauranthub.domain.repository;

import br.com.restauranthub.domain.entity.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {
}