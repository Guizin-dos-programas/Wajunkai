package com.wajunkai.sistemaEstoque.application.ports.inbound.produto;

import com.wajunkai.sistemaEstoque.domain.enums.produto.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.produto.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.model.Produto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface AtualizarProdutoUsecase {
    Produto executar(
            Long id,
            String nome,
            BigDecimal estoqueMinimo,
            UnidadeMedidaProduto unidadeMedida,
            CategoriaProduto categoria,
            LocalDate dataValidade
    );
}
