package com.wajunkai.sistemaEstoque.application.ports.outbound;

import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaQuery;
import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaResultado;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;

public interface MovimentacaoRepositoryPort {

    Movimentacao salvar(Movimentacao movimentacao);
    PaginaResultado<Movimentacao> buscarPorProduto(Long produtoId, PaginaQuery query);
}
