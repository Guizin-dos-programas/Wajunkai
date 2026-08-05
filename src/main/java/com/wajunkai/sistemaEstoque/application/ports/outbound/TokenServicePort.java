package com.wajunkai.sistemaEstoque.application.ports.outbound;

public interface TokenServicePort {

    String gerarToken(String login);
    String validarToken(String token);
}
