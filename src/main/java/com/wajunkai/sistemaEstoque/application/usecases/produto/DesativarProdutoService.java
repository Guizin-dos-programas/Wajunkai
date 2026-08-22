package com.wajunkai.sistemaEstoque.application.usecases.produto;

import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.DesativarProdutoUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import org.springframework.stereotype.Service;

@Service
public class DesativarProdutoService implements DesativarProdutoUsecase {

    private final ProdutoRepositoryPort produtoRepositoryPort;

    public DesativarProdutoService(ProdutoRepositoryPort produtoRepositoryPort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
    }


    @Override
    public Produto executar(Long id) {
        Produto produto = produtoRepositoryPort.buscarPorId(id)
                .orElseThrow(()-> new EntidadeNaoEncontradoException("Produto não encontrado"));

        produto.inativarProduto();

        return produtoRepositoryPort.salvar(produto);
    }
}
