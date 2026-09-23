package com.wajunkai.sistemaEstoque.application.usecases.usuario;

import com.wajunkai.sistemaEstoque.application.ports.inbound.usuario.AtualizarMinhasCredenciaisUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.domain.valueObject.Login;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.usuario.AtualizarMeusDadosRequest;
import org.springframework.stereotype.Service;

@Service
public class AtualizarMinhasCredenciaisService implements AtualizarMinhasCredenciaisUsecase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public AtualizarMinhasCredenciaisService(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoderPort passwordEncoderPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public Usuario executar(Login login, AtualizarMeusDadosRequest atualizarCredenciaisRequest) {
        Usuario usuario = usuarioRepositoryPort.buscarPorLogin(login.valor()).orElseThrow(
                ()-> new EntidadeNaoEncontradoException("Usuário não encontrado")
        );

        String senhaCripto = null;
        if (atualizarCredenciaisRequest.senhaAtualizada() !=null && !atualizarCredenciaisRequest.senhaAtualizada().isBlank()){
            senhaCripto = passwordEncoderPort.encode(atualizarCredenciaisRequest.senhaAtualizada());
        }

        usuario.atualizarMeusDadosPessoais(
                atualizarCredenciaisRequest.nomeAtualizado(),
                senhaCripto,
                atualizarCredenciaisRequest.telefone(),
                atualizarCredenciaisRequest.dataNascimento()
        );
        return usuarioRepositoryPort.salvar(usuario);
    }
}
