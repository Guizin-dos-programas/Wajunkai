package com.wajunkai.sistemaEstoque.domain.ports.outbound;

public interface PasswordEncoderPort {

    String encode(String senhaLimpa);
    boolean matches(String senhaLimpa, String hashSenha);
}
