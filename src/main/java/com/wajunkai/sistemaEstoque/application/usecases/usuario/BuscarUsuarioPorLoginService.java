package com.wajunkai.sistemaEstoque.application.usecases.usuario;

import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;

import com.wajunkai.sistemaEstoque.application.ports.inbound.usuario.BuscarUsuarioPorLoginUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;

public class BuscarUsuarioPorLoginService implements BuscarUsuarioPorLoginUsecase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public BuscarUsuarioPorLoginService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }


    @Override
    public Usuario executar(String login) {
        return usuarioRepositoryPort.buscarPorLogin(login)
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Usuário não encontrado com o login: " + login));
    }
}
