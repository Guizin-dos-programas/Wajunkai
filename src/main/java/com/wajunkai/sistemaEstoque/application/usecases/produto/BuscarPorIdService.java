package com.wajunkai.sistemaEstoque.application.usecases.produto;

import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.BuscarPorIdUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.exceptions.ProdutoNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Produto;

public class BuscarPorIdService implements BuscarPorIdUsecase {

    private final ProdutoRepositoryPort produtoRepositoryPort;

    public BuscarPorIdService(ProdutoRepositoryPort produtoRepositoryPort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
    }


    @Override
    public Produto executar(Long id) {
        Produto produtoId = produtoRepositoryPort.buscarPorId(id)
                .orElseThrow(()-> new ProdutoNaoEncontradoException("Produto não encontrado"));

        return produtoRepositoryPort.salvar(produtoId);
    }
}
