package com.wajunkai.sistemaEstoque.application.usecases.usuario;

import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.application.ports.inbound.usuario.AtualizarUsuarioUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

@Service
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
                ()-> new EntidadeNaoEncontradoException("Usuário não encontrado")
        );

        String senhaCriptografada = null;

        if (novaSenha != null && !novaSenha.isBlank()) {
            senhaCriptografada = passwordEncoderPort.encode(novaSenha);
        }

        usuario.atualizarDadosPessoais(nomeAtualizado, senhaCriptografada);
        return usuarioRepositoryPort.salvar(usuario);
    }
}
