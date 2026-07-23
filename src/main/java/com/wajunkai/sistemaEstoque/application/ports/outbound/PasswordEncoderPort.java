package com.wajunkai.sistemaEstoque.application.ports.outbound;

public interface PasswordEncoderPort {

    String encode(String senhaLimpa);
    boolean matches(String senhaLimpa, String hashSenha);
}
