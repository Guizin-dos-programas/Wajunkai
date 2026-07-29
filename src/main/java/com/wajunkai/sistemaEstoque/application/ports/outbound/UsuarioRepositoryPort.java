package com.wajunkai.sistemaEstoque.application.ports.outbound;

import com.wajunkai.sistemaEstoque.application.dtos.usuario.PaginaResultado;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorId(Long id);

    Optional<Usuario> buscarPorLogin(String login);

    boolean existePorLogin(String loginString);

    PaginaResultado<Usuario> buscarTodosPaginado(int pagina, int tamanho);

}
