package com.wajunkai.sistemaEstoque.domain.model;

import java.util.function.Function;

public class ColunaCsv {

    private final String header;
    private final Function<Movimentacao, String> extrator;

    public ColunaCsv(String header, Function<Movimentacao, String> extrator) {
        this.header = header;
        this.extrator = extrator;
    }

    public String getHeader() {
        return header;
    }

    public String extrair(Movimentacao m) {
        String valor = extrator.apply(m);
        return valor == null ? "" : valor;
    }
}
