package com.wajunkai.sistemaEstoque.infrastructure.persistence.repository;

import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.UsuarioJpaEntity;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.projection.UsuarioNomeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SpringDataUsuarioRepository extends JpaRepository<UsuarioJpaEntity, Long> {

    Optional<UsuarioJpaEntity> findByLogin(String login);

    boolean existsByLogin(String login);

    @Query("SELECT u.id AS id, u.nome AS nome FROM UsuarioJpaEntity u WHERE u.id IN :ids")
    List<UsuarioNomeProjection> buscarNomesPorIds(@Param("ids") Set<Long> ids);
}