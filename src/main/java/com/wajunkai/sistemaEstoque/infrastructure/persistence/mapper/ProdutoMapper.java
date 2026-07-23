package com.wajunkai.sistemaEstoque.infrastructure.persistence.mapper;

import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.ProdutoJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public ProdutoJpaEntity toEntity(Produto domain) {
        if (domain == null) return null;

        return new ProdutoJpaEntity(
                domain.getId(),
                domain.getNome(),
                domain.getQuantidadeAtual(),
                domain.getEstoqueMinimo(),
                domain.getUnidadeMedidaProduto(),
                domain.getCategoriaProduto(),
                domain.getDataValidade()
        );
    }

    public Produto toDomain(ProdutoJpaEntity entity) {
        if (entity == null) return null;

        QuantidadeEstoque quantidadeAtual = entity.getQuantidadeAtual();
        QuantidadeEstoque estoqueMinimo = entity.getEstoqueMinimo();

        return new Produto(
                entity.getId(),
                entity.getNome(),
                quantidadeAtual,
                estoqueMinimo,
                entity.getUnidadeMedidaProduto(),
                entity.getCategoriaProduto(),
                entity.getDataValidade()
        );
    }

}
