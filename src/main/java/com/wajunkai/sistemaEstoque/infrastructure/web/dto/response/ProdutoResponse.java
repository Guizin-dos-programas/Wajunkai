package com.wajunkai.sistemaEstoque.infrastructure.web.dto.response;

import com.wajunkai.sistemaEstoque.domain.enums.produto.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.enums.produto.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;

import java.time.LocalDate;

public record ProdutoResponse(

        Long id,
        String nome,
        QuantidadeEstoque quantidadeAtual,
        QuantidadeEstoque estoqueMinimo,
        UnidadeMedidaProduto unidadeMedidaProduto,
        CategoriaProduto categoriaProduto,
        LocalDate dataValidade,
        boolean estaVencido,
        boolean precisaReposicao,
        Situacao situacao

) {
    public static ProdutoResponse fromDomain(Produto produto){
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getQuantidadeAtual(),
                produto.getEstoqueMinimo(),
                produto.getUnidadeMedidaProduto(),
                produto.getCategoriaProduto(),
                produto.getDataValidade(),
                produto.estaVencido(),
                produto.precisaReposicao(),
                produto.getSituacao()
        );
    }
}
