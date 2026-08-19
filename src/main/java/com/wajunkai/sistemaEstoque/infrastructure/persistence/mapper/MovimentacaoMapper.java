package com.wajunkai.sistemaEstoque.infrastructure.persistence.mapper;

import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.MovimentacaoJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MovimentacaoMapper {


    public MovimentacaoJpaEntity toEntity(Movimentacao domain){
        if (domain == null) return null;

        return new MovimentacaoJpaEntity(
                domain.getId(),
                new ProdutoMapper().toEntity(domain.getProduto()),
                domain.getUsuarioId(),
                domain.getTipoMovimentacao(),
                domain.getQuantidade().valor(),
                domain.getDataHora(),
                domain.getDoadorNome(),
                domain.getCidade(),
                domain.getValorCompra(),
                domain.getResidenteNome()
        );
    }
    public static Movimentacao toDomain(MovimentacaoJpaEntity entity){
        if (entity == null) return  null;

        return new Movimentacao(
                entity.getId(),
                new ProdutoMapper().toDomain(entity.getProduto()),
                entity.getUsuarioId(),
                entity.getTipoMovimentacao(),
                new QuantidadeEstoque(entity.getQuantidade()),
                entity.getDataHora(),
                entity.getDoadorNome(),
                entity.getCidade(),
                entity.getValorCompra(),
                entity.getResidenteNome()
        );
    }

}
