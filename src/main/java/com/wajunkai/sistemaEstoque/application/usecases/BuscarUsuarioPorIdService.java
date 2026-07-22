package com.wajunkai.sistemaEstoque.application.usecases;

import com.wajunkai.sistemaEstoque.domain.exceptions.UsuarioNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.domain.ports.inbound.BuscarUsuarioPorIdUseCase;
import com.wajunkai.sistemaEstoque.domain.ports.outbound.UsuarioRepositoryPort;

public class BuscarUsuarioPorIdService implements BuscarUsuarioPorIdUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public BuscarUsuarioPorIdService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public Usuario executar(Long id) {
        return usuarioRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado para o ID: " + id));
    }
}
