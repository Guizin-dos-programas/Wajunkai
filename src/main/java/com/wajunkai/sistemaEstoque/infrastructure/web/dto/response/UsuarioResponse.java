package com.wajunkai.sistemaEstoque.infrastructure.web.dto.response;

import com.wajunkai.sistemaEstoque.domain.enums.TipoUsuario;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;

import java.time.LocalDateTime;

public record UsuarioResponse(
        String id,
        String nome,
        String login,
        TipoUsuario tipoUsuario,
        boolean ativo,
        LocalDateTime dataCadastro
) {
    public static UsuarioResponse fromDomain(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId().toString(),
                usuario.getNome(),
                usuario.getLogin().valor(),
                usuario.getTipoUsuario(),
                usuario.isAtivo(),
                usuario.getDataCadastro()
        );
    }
}
