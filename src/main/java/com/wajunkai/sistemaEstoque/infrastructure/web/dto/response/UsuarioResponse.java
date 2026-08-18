package com.wajunkai.sistemaEstoque.infrastructure.web.dto.response;

import com.wajunkai.sistemaEstoque.domain.enums.usuario.TipoUsuario;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nome,
        String login,
        TipoUsuario tipoUsuario,
        boolean ativo,
        LocalDateTime dataCadastro,
        String telefone,
        LocalDate dataNascimento
) {
    public static UsuarioResponse fromDomain(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin().valor(),
                usuario.getTipoUsuario(),
                usuario.isAtivo(),
                usuario.getDataCadastro(),
                usuario.getTelefone(),
                usuario.getDataNascimento()
        );
    }
}
