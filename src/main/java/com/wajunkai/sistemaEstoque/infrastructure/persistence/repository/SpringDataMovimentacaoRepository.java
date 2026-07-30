package com.wajunkai.sistemaEstoque.infrastructure.persistence.repository;

import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.MovimentacaoJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMovimentacaoRepository extends JpaRepository<MovimentacaoJpaEntity, Long> {
    Page<MovimentacaoJpaEntity> findByProdutoId(Long produtoId, Pageable pageable);
}
