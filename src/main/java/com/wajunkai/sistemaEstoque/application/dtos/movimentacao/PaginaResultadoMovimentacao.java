package com.wajunkai.sistemaEstoque.application.dtos.movimentacao;

import java.util.List;

public record PaginaResultadoMovimentacao<T>(
        List<T> conteudo,
        int paginaAtual,
        int tamanhoPagina,
        long totalElementos,
        int totalPaginas
) {
}
