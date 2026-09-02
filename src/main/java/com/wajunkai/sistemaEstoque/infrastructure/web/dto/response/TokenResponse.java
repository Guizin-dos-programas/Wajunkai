package com.wajunkai.sistemaEstoque.infrastructure.web.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long tempoExpiracao
) {
    public TokenResponse(String accessToken, String refreshToken, Long tempoExpiracao) {
        this(accessToken, refreshToken, "Bearer", tempoExpiracao);
    }
}
