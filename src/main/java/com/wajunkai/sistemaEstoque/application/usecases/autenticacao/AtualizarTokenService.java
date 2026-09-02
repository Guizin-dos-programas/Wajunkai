package com.wajunkai.sistemaEstoque.application.usecases.autenticacao;

import com.wajunkai.sistemaEstoque.application.ports.inbound.autenticacao.AtualizarTokenUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.RefreshTokenRepositoryPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.RefreshTokenJpaEntity;
import com.wajunkai.sistemaEstoque.infrastructure.security.adapter.JwtAdapter;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.TokenResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class AtualizarTokenService implements AtualizarTokenUsecase {

    private final RefreshTokenRepositoryPort refreshTokenRepositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final JwtAdapter jwtAdapter;

    private final long refreshTokenExpirationDays = 7;

    public AtualizarTokenService(RefreshTokenRepositoryPort refreshTokenRepositoryPort, UsuarioRepositoryPort usuarioRepositoryPort, JwtAdapter jwtAdapter) {
        this.refreshTokenRepositoryPort = refreshTokenRepositoryPort;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.jwtAdapter = jwtAdapter;
    }

    public RefreshTokenJpaEntity criarRefreshToken(Long usuarioId) {

        refreshTokenRepositoryPort.revogarTokensDoUsuario(usuarioId);

        RefreshTokenJpaEntity refreshToken = new RefreshTokenJpaEntity(
                UUID.randomUUID().toString(),
                usuarioId,
                Instant.now().plus(refreshTokenExpirationDays, ChronoUnit.DAYS)
        );

        return refreshTokenRepositoryPort.salvar(refreshToken);
    }

    @Override
    @Transactional
    public TokenResponse executar(String refreshTokenString) {
        RefreshTokenJpaEntity refreshToken = refreshTokenRepositoryPort.buscarPorToken(refreshTokenString)
                .orElseThrow(() -> new IllegalArgumentException("Refresh Token inválido ou não encontrado"));

        if (refreshToken.isRevogado()) {
            throw new IllegalStateException("Refresh Token revogado");
        }

        if (refreshToken.getDataExpiracao().isBefore(Instant.now())) {
            throw new IllegalStateException("Refresh Token expirado. Faça login novamente.");
        }

        Usuario usuario = usuarioRepositoryPort.buscarPorId(refreshToken.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        String novoAccessToken = jwtAdapter.gerarToken(usuario.getLogin().valor());

        refreshToken.setRevogado(true);
        refreshTokenRepositoryPort.salvar(refreshToken);

        RefreshTokenJpaEntity novoRefreshToken = criarRefreshToken(usuario.getId());

        return new TokenResponse(novoAccessToken, novoRefreshToken.getToken(), 1800L);
    }
}
