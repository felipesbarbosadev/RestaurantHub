package br.com.restauranthub.domain.repository;

import br.com.restauranthub.domain.entity.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    boolean existsByNome(String nome);

}