package com.wajunkai.sistemaEstoque.infrastructure.persistence.repository;

import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.ProdutoJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataProdutoRepository extends JpaRepository<ProdutoJpaEntity, Long> {
    boolean existsByNome(String nome);
    Page<ProdutoJpaEntity> findBySituacao(Situacao situacao, Pageable pageable);
}
