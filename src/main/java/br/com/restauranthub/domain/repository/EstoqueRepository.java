package br.com.restauranthub.domain.repository;

import br.com.restauranthub.domain.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
}