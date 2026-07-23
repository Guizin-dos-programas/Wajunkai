package com.wajunkai.sistemaEstoque.application.usecases;

import com.wajunkai.sistemaEstoque.domain.exceptions.UsuarioNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.application.ports.inbound.usuario.DesativarUsuarioUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;

public class DesativarUsuarioService implements DesativarUsuarioUsecase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public DesativarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }


    @Override
    public Usuario executar(Long id) {
        Usuario usuario = usuarioRepositoryPort.buscarPorId(id).orElseThrow(
                ()-> new UsuarioNaoEncontradoException("Usuario Não encontrado")
        );
        usuario.desativarUsuario();

        return usuarioRepositoryPort.salvar(usuario);
    }
}
