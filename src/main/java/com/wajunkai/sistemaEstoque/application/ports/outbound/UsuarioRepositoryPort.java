package com.wajunkai.sistemaEstoque.application.ports.outbound;

import com.wajunkai.sistemaEstoque.application.dtos.usuario.PaginaResultado;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UsuarioRepositoryPort {

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorId(Long id);

    Optional<Usuario> buscarPorLogin(String login);

    boolean existePorLogin(String loginString);

    PaginaResultado<Usuario> buscarTodosPaginado(int pagina, int tamanho);

    Map<Long, String> buscarNomesPorIds(Set<Long> ids);

}
