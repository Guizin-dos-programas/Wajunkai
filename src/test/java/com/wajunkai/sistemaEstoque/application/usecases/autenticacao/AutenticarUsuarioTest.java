package com.wajunkai.sistemaEstoque.application.usecases.autenticacao;

import com.wajunkai.sistemaEstoque.application.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.TokenServicePort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.exceptions.CredenciaisInvalidasException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.RefreshTokenJpaEntity;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.usuario.LoginRequest;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutenticarUsuarioTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepositoryPort;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @Mock
    private TokenServicePort tokenServicePort;

    @Mock
    private AtualizarTokenService atualizarTokenService;

    @InjectMocks
    private AutenticarUsuarioService autenticarUsuarioService;

    @Test
    @DisplayName("Deve autenticar o usuário com sucesso e retornar os tokens")
    void deveAutenticarComSucesso() {
        LoginRequest loginRequest = new LoginRequest("usuario@teste.com", "senha123");
        Long usuarioId = 1L;

        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getId()).thenReturn(usuarioId);
        when(usuarioMock.getSenha()).thenReturn("senhaCodificada");

        RefreshTokenJpaEntity refreshTokenMock = mock(RefreshTokenJpaEntity.class);
        when(refreshTokenMock.getToken()).thenReturn("refresh-token-uuid");

        when(usuarioRepositoryPort.buscarPorLogin("usuario@teste.com")).thenReturn(Optional.of(usuarioMock));
        when(passwordEncoderPort.matches(eq("senha123"), eq("senhaCodificada"))).thenReturn(true);
        when(tokenServicePort.gerarToken("usuario@teste.com")).thenReturn("access-token-jwt");
        when(atualizarTokenService.criarRefreshToken(usuarioId)).thenReturn(refreshTokenMock);

        TokenResponse resposta = autenticarUsuarioService.executar(loginRequest);

        assertNotNull(resposta);
        assertEquals("access-token-jwt", resposta.accessToken());
        assertEquals("refresh-token-uuid", resposta.refreshToken());
        assertEquals(1800L, resposta.tempoExpiracao());

        verify(usuarioRepositoryPort, times(1)).buscarPorLogin("usuario@teste.com");
        verify(passwordEncoderPort, times(1)).matches("senha123", "senhaCodificada");
        verify(tokenServicePort, times(1)).gerarToken("usuario@teste.com");
        verify(atualizarTokenService, times(1)).criarRefreshToken(usuarioId);
    }

    @Test
    @DisplayName("Deve lançar CredenciaisInvalidasException quando o usuário não for encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        LoginRequest loginRequest = new LoginRequest("inexistente@teste.com", "senha123");

        when(usuarioRepositoryPort.buscarPorLogin(loginRequest.login())).thenReturn(Optional.empty());

        CredenciaisInvalidasException excecao = assertThrows(
                CredenciaisInvalidasException.class,
                () -> autenticarUsuarioService.executar(loginRequest)
        );

        assertEquals("Credencias inválidas", excecao.getMessage());
        verify(usuarioRepositoryPort, times(1)).buscarPorLogin(loginRequest.login());
        verify(passwordEncoderPort, never()).matches(any(), any());
        verify(tokenServicePort, never()).gerarToken(any());
        verify(atualizarTokenService, never()).criarRefreshToken(any());
    }

    @Test
    @DisplayName("Deve lançar CredenciaisInvalidasException quando a senha estiver incorreta")
    void deveLancarExcecaoQuandoSenhaIncorreta() {
        LoginRequest loginRequest = new LoginRequest("usuario@teste.com", "senhaErrada");

        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getSenha()).thenReturn("senhaCodificada");

        when(usuarioRepositoryPort.buscarPorLogin(loginRequest.login())).thenReturn(Optional.of(usuarioMock));
        when(passwordEncoderPort.matches(eq("senhaErrada"), eq("senhaCodificada"))).thenReturn(false);

        CredenciaisInvalidasException excecao = assertThrows(
                CredenciaisInvalidasException.class,
                () -> autenticarUsuarioService.executar(loginRequest)
        );

        assertEquals("Credenciais inválidas", excecao.getMessage());
        verify(usuarioRepositoryPort, times(1)).buscarPorLogin(loginRequest.login());
        verify(passwordEncoderPort, times(1)).matches("senhaErrada", "senhaCodificada");
        verify(tokenServicePort, never()).gerarToken(any());
        verify(atualizarTokenService, never()).criarRefreshToken(any());
    }
}