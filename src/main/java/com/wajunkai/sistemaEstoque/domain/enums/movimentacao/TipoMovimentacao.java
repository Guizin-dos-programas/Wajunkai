package com.wajunkai.sistemaEstoque.domain.enums.movimentacao;

public enum TipoMovimentacao {
    ENTRADA_DOACAO,
    ENTRADA_COMPRA,
    SAIDA_CONSUMO,
    SAIDA_PERDA;

    public boolean isEntrada() {
        return this == ENTRADA_DOACAO || this == ENTRADA_COMPRA;
    }

    public boolean isSaida() {
        return this == SAIDA_CONSUMO || this == SAIDA_PERDA;
    }
}
