package com.wajunkai.sistemaEstoque.infrastructure.persistence.repository;

import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoMovimentacao;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.MovimentacaoJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpringDataMovimentacaoRepository extends JpaRepository<MovimentacaoJpaEntity, Long> {
    Page<MovimentacaoJpaEntity> findByProdutoId(Long produtoId, Pageable pageable);

    @Query("""
        SELECT m FROM MovimentacaoJpaEntity m
        WHERE m.dataHora BETWEEN :inicio AND :fim
        AND m.tipoMovimentacao = :tipo
        ORDER BY m.dataHora DESC
    """)
    List<MovimentacaoJpaEntity> buscarPorPeriodoETipo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim, @Param("tipo") TipoMovimentacao tipo);
}
