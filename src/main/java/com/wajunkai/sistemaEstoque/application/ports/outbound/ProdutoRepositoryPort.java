package com.wajunkai.sistemaEstoque.application.ports.outbound;

import com.wajunkai.sistemaEstoque.domain.model.Produto;

import java.util.Optional;

public interface ProdutoRepositoryPort {

    Produto salvar(Produto produto);
    Optional<Produto> buscarPorId(Long id);
    boolean existePorNome(String nome);
}
