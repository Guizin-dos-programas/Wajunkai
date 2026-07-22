package com.wajunkai.sistemaEstoque.application.usecases;

import com.wajunkai.sistemaEstoque.domain.exceptions.UsuarioNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.domain.ports.inbound.AtualizarUsuarioUsecase;
import com.wajunkai.sistemaEstoque.domain.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.domain.ports.outbound.UsuarioRepositoryPort;

public class AtualizarUsuarioService implements AtualizarUsuarioUsecase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public AtualizarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoderPort passwordEncoderPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }


    @Override
    public Usuario executar(Long id, String nomeAtualizado, String novaSenha) {
        Usuario usuario = usuarioRepositoryPort.buscarPorId(id).orElseThrow(
                ()-> new UsuarioNaoEncontradoException("Usuário não encontrado")
        );

        String senhaCriptografada = null;

        if (novaSenha != null && !novaSenha.isBlank()) {
            senhaCriptografada = passwordEncoderPort.encode(novaSenha);
        }

        usuario.atualizarDadosPessoais(nomeAtualizado, senhaCriptografada);
        return usuarioRepositoryPort.salvar(usuario);
    }
}
