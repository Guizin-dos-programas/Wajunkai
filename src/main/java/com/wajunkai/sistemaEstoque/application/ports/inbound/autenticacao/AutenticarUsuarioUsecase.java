package com.wajunkai.sistemaEstoque.application.ports.inbound.autenticacao;

import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.usuario.LoginRequest;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.TokenResponse;

public interface AutenticarUsuarioUsecase {

    TokenResponse executar(LoginRequest loginRequest);
}
