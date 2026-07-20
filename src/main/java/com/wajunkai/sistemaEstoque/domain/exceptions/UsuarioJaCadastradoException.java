package com.wajunkai.sistemaEstoque.domain.exceptions;

public class UsuarioJaCadastradoException extends RuntimeException {
    public UsuarioJaCadastradoException(String message) {
        super(message);
    }
}
