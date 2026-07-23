package com.wajunkai.sistemaEstoque.domain.enums;

public enum UnidadeMedidaProduto {

    UNIDADE("UN", false),
    QUILOGRAMA("KG", true),
    LITRO("L", true),
    CAIXA("CX", false),
    PACOTE("PCT", false);

    private final String sigla;
    private final boolean fracionavel;

    UnidadeMedidaProduto(String sigla, boolean fracionavel) {
        this.sigla = sigla;
        this.fracionavel = fracionavel;
    }

    public String getSigla() { return sigla; }
    public boolean isFracionavel() { return fracionavel; }
}
