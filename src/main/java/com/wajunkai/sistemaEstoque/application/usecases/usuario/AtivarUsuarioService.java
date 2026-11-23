package com.wajunkai.sistemaEstoque.application.usecases.usuario;

import com.wajunkai.sistemaEstoque.application.ports.inbound.usuario.AtivarUsuarioUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class AtivarUsuarioService implements AtivarUsuarioUsecase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public AtivarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }


    @Override
    public Usuario executar(Long id) {
        Usuario usuario = usuarioRepositoryPort.buscarPorId(id).orElseThrow(
                ()-> new EntidadeNaoEncontradoException("Usuario Não encontrado")
        );
        usuario.ativarUsuario();

        return usuarioRepositoryPort.salvar(usuario);
    }
}
