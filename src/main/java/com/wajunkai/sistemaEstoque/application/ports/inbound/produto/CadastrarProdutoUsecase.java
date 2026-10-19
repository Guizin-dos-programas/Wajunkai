package com.wajunkai.sistemaEstoque.application.ports.inbound.produto;

import com.wajunkai.sistemaEstoque.domain.enums.produto.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.enums.produto.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.model.Produto;

import java.math.BigDecimal;

public interface CadastrarProdutoUsecase {

    Produto executar(
            String nome,
            BigDecimal quantidadeAtual,
            BigDecimal estoqueMinimo,
            UnidadeMedidaProduto unidadeMedidaProduto,
            CategoriaProduto categoria,
            Situacao situacao
    );

}
