package com.wajunkai.sistemaEstoque.application.usecases.autenticacao;

import com.wajunkai.sistemaEstoque.application.dtos.auths.request.LoginRequest;
import com.wajunkai.sistemaEstoque.application.dtos.auths.response.TokenResponse;
import com.wajunkai.sistemaEstoque.application.ports.inbound.autenticacao.AutenticarUsuarioUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.TokenServicePort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.exceptions.CredenciaisInvalidasException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class AutenticarUsuarioService implements AutenticarUsuarioUsecase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenServicePort tokenServicePort;

    public AutenticarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoderPort passwordEncoderPort, TokenServicePort tokenServicePort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenServicePort = tokenServicePort;
    }

    @Override
    public TokenResponse executar(LoginRequest loginRequest) {
        Usuario usuario = usuarioRepositoryPort.buscarPorLogin(loginRequest.login())
                .orElseThrow(()-> new CredenciaisInvalidasException("Credencias inválidas"));

        if (!passwordEncoderPort.matches(loginRequest.senha(), usuario.getSenha())){
            throw new CredenciaisInvalidasException("Credenciais inválidas");
        }

        String token = tokenServicePort.gerarToken(loginRequest.login());
        return new TokenResponse(token, "Bearer", 7200L);
    }
}
