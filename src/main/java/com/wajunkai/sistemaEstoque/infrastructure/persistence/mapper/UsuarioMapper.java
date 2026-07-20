package com.wajunkai.sistemaEstoque.infrastructure.persistence.mapper;

import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.domain.valueObject.Login;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.UsuarioJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioJpaEntity toEntity(Usuario domain) {
        if (domain == null) return null;

        return new UsuarioJpaEntity(
                domain.getId(),
                domain.getNome(),
                domain.getLogin().valor(),
                domain.getSenha(),
                domain.getTipoUsuario(),
                domain.isAtivo(),
                domain.getDataCadastro()
        );
    }

    public Usuario toDomain(UsuarioJpaEntity entity) {
        if (entity == null) return null;

        Login loginVO = new Login(entity.getLogin(), null);

        return new Usuario(
                entity.getId(),
                entity.getNome(),
                loginVO,
                entity.getSenha(),
                entity.getTipoUsuario(),
                entity.isAtivo(),
                entity.getDataCadastro()
        );
    }
}