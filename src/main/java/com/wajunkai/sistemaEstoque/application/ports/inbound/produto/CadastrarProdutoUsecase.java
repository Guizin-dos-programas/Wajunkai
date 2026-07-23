package com.wajunkai.sistemaEstoque.application.ports.inbound.produto;

import com.wajunkai.sistemaEstoque.domain.enums.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;

import java.time.LocalDate;

public interface CadastrarProdutoUsecase {

    Produto executar(
            String nome,
            QuantidadeEstoque quantidadeAtual,
            QuantidadeEstoque estoqueMinimo,
            UnidadeMedidaProduto unidadeMedidaProduto,
            CategoriaProduto categoria,
            LocalDate dataValidade
    );

}
