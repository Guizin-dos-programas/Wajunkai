package com.wajunkai.sistemaEstoque.application.dtos.movimentacao;

public record PaginaQueryMovimentacao(int pagina, int tamanho) {
    public PaginaQueryMovimentacao {
        if (pagina < 0) pagina = 0;
        if (tamanho <= 0) tamanho = 0;
    }
}
