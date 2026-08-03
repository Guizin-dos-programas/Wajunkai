package com.wajunkai.sistemaEstoque.infrastructure.persistence.repository;

import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.MovimentacaoJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpringDataMovimentacaoRepository extends JpaRepository<MovimentacaoJpaEntity, Long> {
    Page<MovimentacaoJpaEntity> findByProdutoId(Long produtoId, Pageable pageable);

    @Query("""
        SELECT COALESCE(SUM(m.valorCompra), 0)
        FROM MovimentacaoJpaEntity m
        WHERE m.tipoMovimentacao = com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoMovimentacao.ENTRADA_COMPRA
        AND m.dataHora BETWEEN :inicio AND :fim
    """)
    BigDecimal somarTotalGastoComCompras(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("""
        SELECT COUNT(m)
        FROM MovimentacaoJpaEntity m
        WHERE m.tipoMovimentacao = com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoMovimentacao.ENTRADA_COMPRA
        AND m.dataHora BETWEEN :inicio AND :fim
    """)
    Long contarTotalComprasNoPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("""
        SELECT m.produto.categoriaProduto, SUM(m.valorCompra)
        FROM MovimentacaoJpaEntity m
        WHERE m.tipoMovimentacao = com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoMovimentacao.ENTRADA_COMPRA
        AND m.dataHora BETWEEN :inicio AND :fim
        GROUP BY m.produto.categoriaProduto
    """)
    List<Object[]> agruparGastosPorCategoria(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
