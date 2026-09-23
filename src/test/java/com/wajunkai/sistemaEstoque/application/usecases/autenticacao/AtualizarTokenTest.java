package com.wajunkai.sistemaEstoque.application.usecases.autenticacao;

import com.wajunkai.sistemaEstoque.application.ports.outbound.RefreshTokenRepositoryPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.domain.valueObject.Login;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.RefreshTokenJpaEntity;
import com.wajunkai.sistemaEstoque.infrastructure.security.adapter.JwtAdapter;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarTokenTest {

    @Mock
    private RefreshTokenRepositoryPort refreshTokenRepositoryPort;

    @Mock
    private UsuarioRepositoryPort usuarioRepositoryPort;

    @Mock
    private JwtAdapter jwtAdapter;

    @InjectMocks
    private AtualizarTokenService atualizarTokenService;

    @Test
    @DisplayName("Deve atualizar o token com sucesso quando o refresh token for válido")
    void deveAtualizarTokenComSucesso() {
        String tokenString = "valid-refresh-token";
        Long usuarioId = 1L;

        RefreshTokenJpaEntity refreshTokenMock = mock(RefreshTokenJpaEntity.class);
        when(refreshTokenMock.isRevogado()).thenReturn(false);
        when(refreshTokenMock.getDataExpiracao()).thenReturn(Instant.now().plus(1, ChronoUnit.DAYS));
        when(refreshTokenMock.getUsuarioId()).thenReturn(usuarioId);

        Usuario usuarioMock = mock(Usuario.class);
        Login loginMock = mock(Login.class);
        when(loginMock.valor()).thenReturn("usuario@teste.com");
        when(usuarioMock.getLogin()).thenReturn(loginMock);
        when(usuarioMock.getId()).thenReturn(usuarioId);

        when(refreshTokenRepositoryPort.buscarPorToken(tokenString)).thenReturn(Optional.of(refreshTokenMock));
        when(usuarioRepositoryPort.buscarPorId(usuarioId)).thenReturn(Optional.of(usuarioMock));
        when(jwtAdapter.gerarToken("usuario@teste.com")).thenReturn("novo-access-token");

        when(refreshTokenRepositoryPort.salvar(any(RefreshTokenJpaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TokenResponse resposta = atualizarTokenService.executar(tokenString);

        assertNotNull(resposta);
        assertEquals("novo-access-token", resposta.accessToken());
        assertNotNull(resposta.refreshToken());
        assertEquals(1800L, resposta.tempoExpiracao());

        verify(refreshTokenMock, times(1)).setRevogado(true);
        verify(refreshTokenRepositoryPort, times(2)).salvar(any(RefreshTokenJpaEntity.class));
        verify(refreshTokenRepositoryPort, times(1)).revogarTokensDoUsuario(usuarioId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o refresh token não for encontrado")
    void deveLancarExcecaoQuandoTokenNaoEncontrado() {
        String tokenString = "token-inexistente";
        when(refreshTokenRepositoryPort.buscarPorToken(tokenString)).thenReturn(Optional.empty());

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> atualizarTokenService.executar(tokenString)
        );

        assertEquals("Refresh Token inválido ou não encontrado", excecao.getMessage());
        verify(refreshTokenRepositoryPort, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o refresh token estiver revogado")
    void deveLancarExcecaoQuandoTokenRevogado() {
        String tokenString = "token-revogado";
        RefreshTokenJpaEntity refreshTokenMock = mock(RefreshTokenJpaEntity.class);
        when(refreshTokenMock.isRevogado()).thenReturn(true);

        when(refreshTokenRepositoryPort.buscarPorToken(tokenString)).thenReturn(Optional.of(refreshTokenMock));

        IllegalStateException excecao = assertThrows(
                IllegalStateException.class,
                () -> atualizarTokenService.executar(tokenString)
        );

        assertEquals("Refresh Token revogado", excecao.getMessage());
        verify(refreshTokenRepositoryPort, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o refresh token estiver expirado")
    void deveLancarExcecaoQuandoTokenExpirado() {
        String tokenString = "token-expirado";
        RefreshTokenJpaEntity refreshTokenMock = mock(RefreshTokenJpaEntity.class);
        when(refreshTokenMock.isRevogado()).thenReturn(false);
        when(refreshTokenMock.getDataExpiracao()).thenReturn(Instant.now().minus(1, ChronoUnit.DAYS));

        when(refreshTokenRepositoryPort.buscarPorToken(tokenString)).thenReturn(Optional.of(refreshTokenMock));

        IllegalStateException excecao = assertThrows(
                IllegalStateException.class,
                () -> atualizarTokenService.executar(tokenString)
        );

        assertEquals("Refresh Token expirado. Faça login novamente.", excecao.getMessage());
        verify(refreshTokenRepositoryPort, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário associado ao token não for encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        String tokenString = "valid-refresh-token";
        Long usuarioId = 99L;

        RefreshTokenJpaEntity refreshTokenMock = mock(RefreshTokenJpaEntity.class);
        when(refreshTokenMock.isRevogado()).thenReturn(false);
        when(refreshTokenMock.getDataExpiracao()).thenReturn(Instant.now().plus(1, ChronoUnit.DAYS));
        when(refreshTokenMock.getUsuarioId()).thenReturn(usuarioId);

        when(refreshTokenRepositoryPort.buscarPorToken(tokenString)).thenReturn(Optional.of(refreshTokenMock));
        when(usuarioRepositoryPort.buscarPorId(usuarioId)).thenReturn(Optional.empty());

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> atualizarTokenService.executar(tokenString)
        );

        assertEquals("Usuário não encontrado", excecao.getMessage());
        verify(refreshTokenRepositoryPort, never()).salvar(any());
    }
}