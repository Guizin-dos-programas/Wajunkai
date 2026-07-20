package com.wajunkai.sistemaEstoque.domain.ports.outbound;

import com.wajunkai.sistemaEstoque.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorId(String id);

    Optional<Usuario> buscarPorLogin(String login);

    boolean existePorLogin(String loginString);
}
