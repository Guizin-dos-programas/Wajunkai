package com.wajunkai.sistemaEstoque.application.usecases.usuario;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.wajunkai.sistemaEstoque.application.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.usuario.TipoUsuario;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeJaCadastradaException;
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

@ExtendWith(MockitoExtension.class)
public class CadastrarUsuarioServiceTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepositoryPort;
    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private  CadastrarUsuarioService cadastrarUsuarioService;

    @Test
    @DisplayName("Deve cadastrar usuário com sucesso se dados válidos")
    void deveCadastrarUsuarioSemErro(){

        Login login = new Login("roberto@gmail.com", null);
        String senhaCriptografada = "senha-criptografada";

        when(passwordEncoderPort.encode("12345")).thenReturn(senhaCriptografada);

        Usuario usuario = new Usuario(
                "Roberto",
                login,
                "12345",
                TipoUsuario.ADMIN,
                LocalDate.of(2007, 3, 29),
                "44998576541"
        );

        Usuario usuarioSalvo = new Usuario(1L, "Roberto", login, senhaCriptografada, TipoUsuario.ADMIN, LocalDate.of(2007,3,29), "44998576541", true, LocalDateTime.now());

        when(usuarioRepositoryPort.salvar(any(Usuario.class))).thenReturn(usuarioSalvo);

        Usuario resultado = cadastrarUsuarioService.executar(usuario.getNome(),usuario.getLogin().valor(), usuario.getSenha(), usuario.getTipoUsuario(), usuario.getTelefone(), usuario.getDataNascimento());

        LocalDate dataNasc = LocalDate.of(2007, 3, 29);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Roberto", resultado.getNome());
        assertEquals(login, resultado.getLogin());
        assertEquals(senhaCriptografada, resultado.getSenha());
        assertEquals(TipoUsuario.ADMIN, resultado.getTipoUsuario());
        assertEquals("44998576541", resultado.getTelefone());
        assertEquals(dataNasc, resultado.getDataNascimento());


        verify(usuarioRepositoryPort).salvar(argThat(u ->
                u.getNome().equals("Roberto") &&
                        u.getLogin().equals(login) &&
                        u.getSenha().equals(senhaCriptografada) &&
                        u.getTipoUsuario() == TipoUsuario.ADMIN &&
                        u.getTelefone().equals("44998576541") &&
                        u.getDataNascimento().equals(dataNasc)
        ));
    }


    @Test
    @DisplayName("Se login já existir no banco dar erro")
    void darErroSeLoginCadastrado(){
        Login login = new Login("roberto@gmail.com", null);
        String senhaCriptografada = passwordEncoderPort.encode("senha-criptografada");
        Usuario usuario = new Usuario(
                "Roberto",
                login,
                senhaCriptografada,
                TipoUsuario.ADMIN,
                LocalDate.of(2007, 3, 29),
                "44998576541"
        );

        when(usuarioRepositoryPort.existePorLogin("roberto@gmail.com")).thenReturn(true);

        assertThrows(
                EntidadeJaCadastradaException.class,
                () -> cadastrarUsuarioService.executar(usuario.getNome(), usuario.getLogin().valor(), usuario.getSenha(), usuario.getTipoUsuario(), usuario.getTelefone(), usuario.getDataNascimento())
        );
        verify(usuarioRepositoryPort, never()).salvar(any());

    }
}
