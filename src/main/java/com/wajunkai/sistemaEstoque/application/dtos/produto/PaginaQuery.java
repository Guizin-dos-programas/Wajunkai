package com.wajunkai.sistemaEstoque.application.dtos.produto;

import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;

public record PaginaQuery(int pagina, int tamanho, Situacao situacao) {
    public PaginaQuery {
        if (pagina < 0) pagina = 0;
        if (tamanho <= 0) tamanho = 0;
        if (situacao == null) situacao = Situacao.ATIVO;
    }
}
