package com.wajunkai.sistemaEstoque.application.ports.inbound.autenticacao;

import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.TokenResponse;

public interface AtualizarTokenUsecase {
    TokenResponse executar(String refreshTokenString);
}
