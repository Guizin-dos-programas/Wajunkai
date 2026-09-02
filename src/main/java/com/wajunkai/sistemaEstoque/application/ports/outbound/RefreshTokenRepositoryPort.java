package com.wajunkai.sistemaEstoque.application.ports.outbound;

import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.RefreshTokenJpaEntity;

import java.util.Optional;

public interface RefreshTokenRepositoryPort {
    RefreshTokenJpaEntity salvar(RefreshTokenJpaEntity refreshToken);
    Optional<RefreshTokenJpaEntity> buscarPorToken(String token);
    void revogarTokensDoUsuario(Long usuarioId);
}
