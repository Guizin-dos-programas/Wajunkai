package com.wajunkai.sistemaEstoque.application.usecases.usuario;

import com.wajunkai.sistemaEstoque.application.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.usuario.TipoUsuario;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.domain.valueObject.Login;
import org.junit.jupiter.api.BeforeEach;
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
public class AtualizarUsuarioTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepositoryPort;
    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private AtualizarUsuarioService atualizarUsuarioService;

    @BeforeEach
    void setUp(){
        atualizarUsuarioService = new AtualizarUsuarioService(usuarioRepositoryPort, passwordEncoderPort);
    }

    @Test
    @DisplayName("Deve atualizar as informações do usuario sem erro!")
    void deveAtualizarUsuarioComSucesso(){

        Login login = new Login("jorge@gmail.com", null);

        Usuario usuarioExistente = new Usuario(
                1L,
                "Jorge",
                login,
                "senha-criptografada",
                TipoUsuario.ADMIN,
                LocalDate.of(2007, 3, 29),
                "44998576541",
                true,
                LocalDateTime.now()
        );

        when(usuarioRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(usuarioExistente));
        when(passwordEncoderPort.encode("12345")).thenReturn("Senha nova");
        when(usuarioRepositoryPort.salvar(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = atualizarUsuarioService.executar(
                1L,
                "Jorge Aragão",
                "12345",
                "44998576541",
                LocalDate.of(2007, 3, 29)
        );

        LocalDate nasc = LocalDate.of(2007,3,29);

        assertEquals("Jorge Aragão",resultado.getNome());
        assertEquals("44998576541", resultado.getTelefone());
        assertEquals(nasc, resultado.getDataNascimento());

        verify(usuarioRepositoryPort).buscarPorId(1L);
        verify(passwordEncoderPort).encode("12345");
        verify(usuarioRepositoryPort).salvar(any(Usuario.class));
    }

    @Test
    @DisplayName("Não atualiza usuario se não for encontrado")
    void deveLancarExcecaoSeUsuarioNaoExiste(){

        when(usuarioRepositoryPort.buscarPorId(1L)).thenReturn(Optional.empty());

        EntidadeNaoEncontradoException entidadeNaoEncontradoException = assertThrows(
                EntidadeNaoEncontradoException.class,
                () -> atualizarUsuarioService.executar(1L, "Jorge Aragão", "12345", "44998576541", LocalDate.of(2007, 3, 29))
        );

        assertEquals("Usuário não encontrado", entidadeNaoEncontradoException.getMessage());
        verify(usuarioRepositoryPort).buscarPorId(1L);
        verify(usuarioRepositoryPort, never()).salvar(any(Usuario.class));
    }

    @Test
    @DisplayName("Da erro se a senha passada for nula")
    void deveLancarExcecaoSeSenhaNula(){


        Login login = new Login("jorge@gmail.com", null);

        Usuario usuarioExistente = new Usuario(
                1L,
                "Jorge",
                login,
                "senha-antiga",
                TipoUsuario.ADMIN,
                LocalDate.of(2007, 3, 29),
                "44998576541",
                true,
                LocalDateTime.now()
        );

        when(usuarioRepositoryPort.buscarPorId(1L))
                .thenReturn(Optional.of(usuarioExistente));

        when(usuarioRepositoryPort.salvar(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = atualizarUsuarioService.executar(
                1L,
                "Jorge Aragao",
                null,
                "44998576541",
                LocalDate.of(2007, 3, 29)
        );

        LocalDate nasc = LocalDate.of(2007, 3, 29);

        assertEquals("Jorge Aragao", resultado.getNome());
        assertEquals("44998576541", resultado.getTelefone());
        assertEquals(nasc, resultado.getDataNascimento());

        verify(usuarioRepositoryPort).buscarPorId(1L);
        verify(usuarioRepositoryPort).salvar(any(Usuario.class));

        verify(passwordEncoderPort, never()).encode(any());
    }
}
