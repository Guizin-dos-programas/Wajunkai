package com.wajunkai.sistemaEstoque.application.ports.inbound.autenticacao;

import com.wajunkai.sistemaEstoque.application.dtos.auths.request.LoginRequest;
import com.wajunkai.sistemaEstoque.application.dtos.auths.response.TokenResponse;

public interface AutenticarUsuarioUsecase {

    TokenResponse executar(LoginRequest loginRequest);
}
