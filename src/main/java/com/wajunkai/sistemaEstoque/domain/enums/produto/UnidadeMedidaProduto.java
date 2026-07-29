package com.wajunkai.sistemaEstoque.domain.enums.produto;

public enum UnidadeMedidaProduto {

    UN("UN", false),
    KG("KG", true),
    L("L", true),
    CX("CX", false),
    PCT("PCT", false);

    private final String sigla;
    private final boolean fracionavel;

    UnidadeMedidaProduto(String sigla, boolean fracionavel) {
        this.sigla = sigla;
        this.fracionavel = fracionavel;
    }


    public String getSigla() { return sigla; }
    public boolean isFracionavel() { return fracionavel; }
}
