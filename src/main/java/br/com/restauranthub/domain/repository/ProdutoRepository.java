package br.com.restauranthub.domain.repository;

import br.com.restauranthub.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByNome(String nome);

}