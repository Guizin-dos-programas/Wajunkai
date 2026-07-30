package com.wajunkai.sistemaEstoque.application.usecases.produto;

import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.AtivarProdutoUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Produto;

public class AtivarProdutoService implements AtivarProdutoUsecase {

    private final ProdutoRepositoryPort produtoRepositoryPort;

    public AtivarProdutoService(ProdutoRepositoryPort produtoRepositoryPort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
    }

    @Override
    public Produto executar(Long id) {
        Produto produto = produtoRepositoryPort.buscarPorId(id)
                .orElseThrow(()-> new EntidadeNaoEncontradoException("Produto não encontrado"));

        produto.ativarProduto();
        return produtoRepositoryPort.salvar(produto);
    }
}
