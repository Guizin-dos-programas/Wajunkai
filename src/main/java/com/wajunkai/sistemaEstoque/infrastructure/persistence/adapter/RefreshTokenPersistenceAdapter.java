package com.wajunkai.sistemaEstoque.infrastructure.persistence.adapter;

import com.wajunkai.sistemaEstoque.application.ports.outbound.RefreshTokenRepositoryPort;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.RefreshTokenJpaEntity;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.repository.SpringDataRefreshTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RefreshTokenPersistenceAdapter implements RefreshTokenRepositoryPort {

    private final SpringDataRefreshTokenRepository springDataRepository;

    public RefreshTokenPersistenceAdapter(SpringDataRefreshTokenRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }


    @Override
    public RefreshTokenJpaEntity salvar(RefreshTokenJpaEntity refreshToken) {
        return springDataRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshTokenJpaEntity> buscarPorToken(String token) {
        return springDataRepository.findByToken(token);
    }

    @Override
    public void revogarTokensDoUsuario(Long usuarioId) {
        springDataRepository.revogarTodosDoUsuario(usuarioId);
    }

}