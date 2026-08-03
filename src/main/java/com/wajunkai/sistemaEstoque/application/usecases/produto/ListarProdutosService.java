package com.wajunkai.sistemaEstoque.application.usecases.produto;

import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaQuery;
import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaResultado;
import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.ListarProdutosUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import org.springframework.stereotype.Service;

@Service
public class ListarProdutosService implements ListarProdutosUsecase {

    private final ProdutoRepositoryPort produtoRepositoryPort;

    public ListarProdutosService(ProdutoRepositoryPort produtoRepositoryPort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
    }

    @Override
    public PaginaResultado<Produto> executar(PaginaQuery paginaQuery, Situacao situacao) {

        Situacao situacaoBusca = (situacao != null) ? situacao : Situacao.ATIVO;
        return produtoRepositoryPort.listarTodos(paginaQuery, situacaoBusca);
    }
}
