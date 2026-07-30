package com.wajunkai.sistemaEstoque.application.ports.inbound.produto;

import com.wajunkai.sistemaEstoque.domain.model.Produto;

public interface AtivarProdutoUsecase {

    Produto executar(Long id);
}
