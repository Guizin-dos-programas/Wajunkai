package com.wajunkai.sistemaEstoque.application.usecases.usuario;

import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.usuario.TipoUsuario;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.domain.valueObject.Login;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DesativarUsuarioTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepositoryPort;

    @InjectMocks
    private DesativarUsuarioService desativarUsuarioService;

    @Test
    @DisplayName("Deve fazer a exclusão lógica do usuário, sem dar erro")
    void deveDesativarUsuarioSemerro(){

        Usuario usuario = new Usuario(
                1L,
                "anonimo",
                new Login("anonimo", null),
                "anonimo123",
                TipoUsuario.FUNCIONARIO,
                LocalDate.of(2007, 3, 29),
                "44998576541",
                true,
                LocalDateTime.now()
        );

        when(usuarioRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        when(usuarioRepositoryPort.salvar(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = desativarUsuarioService.executar(1L);

        assertEquals(1L, resultado.getId());
        assertFalse(resultado.isAtivo());

        verify(usuarioRepositoryPort).buscarPorId(1L);
        verify(usuarioRepositoryPort).salvar(any(Usuario.class));
    }

    @Test
    @DisplayName("Retorna exceção EntidadeNaoEncontradaException, se id for nulo ou inexistente")
    void deveLancarExcecaoSeUsuarioInexistente(){

        when(usuarioRepositoryPort.buscarPorId(1L)).thenReturn(Optional.empty());

        EntidadeNaoEncontradoException entidadeNaoEncontradoException = assertThrows(
                EntidadeNaoEncontradoException.class,
                ()-> desativarUsuarioService.executar(1L)
        );

        assertEquals("Usuario Não encontrado", entidadeNaoEncontradoException.getMessage());
        verify(usuarioRepositoryPort).buscarPorId(1L);
        verify(usuarioRepositoryPort, never()).salvar(any(Usuario.class));
    }
}
