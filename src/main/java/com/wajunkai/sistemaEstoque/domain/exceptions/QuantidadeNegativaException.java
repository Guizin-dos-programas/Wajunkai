package com.wajunkai.sistemaEstoque.domain.exceptions;

public class QuantidadeNegativaException extends RuntimeException {
    public QuantidadeNegativaException(String message) {
        super(message);
    }
}
