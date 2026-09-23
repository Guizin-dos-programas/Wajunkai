package com.wajunkai.sistemaEstoque.infrastructure.persistence.repository;

import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.RefreshTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringDataRefreshTokenRepository extends JpaRepository<RefreshTokenJpaEntity, Long> {

    Optional<RefreshTokenJpaEntity> findByToken(String token);

    @Modifying
    @Query("UPDATE RefreshTokenJpaEntity r SET r.revogado = true WHERE r.usuarioId = :usuarioId AND r.revogado = false")
    void revogarTodosDoUsuario(@Param("usuarioId") Long usuarioId);
}
