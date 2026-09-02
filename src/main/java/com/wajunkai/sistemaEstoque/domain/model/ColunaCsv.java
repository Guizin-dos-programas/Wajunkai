package com.wajunkai.sistemaEstoque.domain.model;

import java.util.Map;
import java.util.function.BiFunction;

public class ColunaCsv {

    private final String header;
    private final BiFunction<Movimentacao, Map<Long, String>, String> extrator;

    public ColunaCsv(String header, BiFunction<Movimentacao, Map<Long, String>, String> extrator) {
        this.header = header;
        this.extrator = extrator;
    }

    public String getHeader() {
        return header;
    }

    public String extrair(Movimentacao movimentacao, Map<Long, String> nomesUsuarios) {
        String valor = extrator.apply(movimentacao, nomesUsuarios);
        return valor == null ? "" : valor;
    }
}