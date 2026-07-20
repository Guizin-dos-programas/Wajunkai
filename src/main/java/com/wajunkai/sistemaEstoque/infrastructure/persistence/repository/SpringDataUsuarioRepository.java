package com.wajunkai.sistemaEstoque.infrastructure.persistence.repository;

import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataUsuarioRepository extends JpaRepository<UsuarioJpaEntity, String> {

    Optional<UsuarioJpaEntity> findByLogin(String login);

    boolean existsByLogin(String login);
}