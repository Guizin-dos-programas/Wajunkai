package com.wajunkai.sistemaEstoque.application.usecases.produto;

import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.DesativarProdutoUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.model.Produto;

public class DesativarProdutoService implements DesativarProdutoUsecase {

    private final ProdutoRepositoryPort produtoRepositoryPort;

    public DesativarProdutoService(ProdutoRepositoryPort produtoRepositoryPort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
    }


    @Override
    public Produto executar(Long id) {
        return null;
    }
}
