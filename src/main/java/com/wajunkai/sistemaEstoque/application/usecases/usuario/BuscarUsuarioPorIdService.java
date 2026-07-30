package com.wajunkai.sistemaEstoque.application.usecases.usuario;

import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.application.ports.inbound.usuario.BuscarUsuarioPorIdUseCase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;

public class BuscarUsuarioPorIdService implements BuscarUsuarioPorIdUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public BuscarUsuarioPorIdService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public Usuario executar(Long id) {
        return usuarioRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Usuário não encontrado para o ID: " + id));
    }
}
