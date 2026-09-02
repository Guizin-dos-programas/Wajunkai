package com.wajunkai.sistemaEstoque.application.usecases.autenticacao;

import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.RefreshTokenJpaEntity;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.usuario.LoginRequest;
import com.wajunkai.sistemaEstoque.application.ports.inbound.autenticacao.AutenticarUsuarioUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.TokenServicePort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.exceptions.CredenciaisInvalidasException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.TokenResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AutenticarUsuarioService implements AutenticarUsuarioUsecase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenServicePort tokenServicePort;
    private final AtualizarTokenService atualizarTokenService;

    public AutenticarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoderPort passwordEncoderPort, TokenServicePort tokenServicePort, AtualizarTokenService atualizarTokenService) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenServicePort = tokenServicePort;
        this.atualizarTokenService = atualizarTokenService;
    }

    @Override
    @Transactional
    public TokenResponse executar(LoginRequest loginRequest) {
        Usuario usuario = usuarioRepositoryPort.buscarPorLogin(loginRequest.login())
                .orElseThrow(()-> new CredenciaisInvalidasException("Credencias inválidas"));

        if (!passwordEncoderPort.matches(loginRequest.senha(), usuario.getSenha())){
            throw new CredenciaisInvalidasException("Credenciais inválidas");
        }

        String token = tokenServicePort.gerarToken(loginRequest.login());

        RefreshTokenJpaEntity refreshToken = atualizarTokenService.criarRefreshToken(usuario.getId());
        return new TokenResponse(token, refreshToken.getToken(), 1800L);
    }
}
