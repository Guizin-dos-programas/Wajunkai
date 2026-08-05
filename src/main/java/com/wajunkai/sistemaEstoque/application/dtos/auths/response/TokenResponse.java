package com.wajunkai.sistemaEstoque.application.dtos.auths.response;

public record TokenResponse(
        String token,
        String tipo,
        Long tempoExpiracao
){}
