package com.wajunkai.sistemaEstoque.application.usecases.usuario;

import com.wajunkai.sistemaEstoque.application.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.usuario.TipoUsuario;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.domain.valueObject.Login;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.usuario.AtualizarMeusDadosRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtualizarMinhasCredenciasTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepositoryPort;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private AtualizarMinhasCredenciaisService atualizarMinhasCredenciaisService;

    @Test
    @DisplayName("Atualiza as credenciais próprias do usuário")
    void deveAtualizarCredencias(){

        Login login = new Login("usuario@gmail.com", null);

        AtualizarMeusDadosRequest atualizarMeusDadosRequest = new AtualizarMeusDadosRequest("Nome Atualizado",
                "novaSenha123",
                "44999999999",
                LocalDate.of(2005, 5, 15));

        Usuario usuarioExistente = new Usuario("joao", login, "sakdjdkaj", TipoUsuario.FUNCIONARIO, LocalDate.of(2000, 1, 1), "44992516541");

        LocalDate nasc = LocalDate.of(2005, 5, 15);

        when(usuarioRepositoryPort.buscarPorLogin(login.valor())).thenReturn(Optional.of(usuarioExistente));
        when(passwordEncoderPort.encode("novaSenha123")).thenReturn("hash");
        when(usuarioRepositoryPort.salvar(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario usuario = atualizarMinhasCredenciaisService.executar(login, atualizarMeusDadosRequest);

        assertEquals("Nome Atualizado",usuario.getNome());
        assertEquals("hash", usuario.getSenha());
        assertEquals("44999999999", usuario.getTelefone());
        assertEquals(nasc, usuario.getDataNascimento());

        verify(usuarioRepositoryPort, times(1)).buscarPorLogin(login.valor());
        verify(usuarioRepositoryPort, times(1)).salvar(any(Usuario.class));
    }

    @Test
    @DisplayName("Lança exceção caso Login não encontrado")
    void deveLancarExcecao(){

        Login login = new Login("usuario@gmail.com", null);

        AtualizarMeusDadosRequest atualizarMeusDadosRequest = new AtualizarMeusDadosRequest("Nome Atualizado",
                "novaSenha123",
                "44999999999",
                LocalDate.of(2005, 5, 15));

        when(usuarioRepositoryPort.buscarPorLogin(login.valor())).thenReturn(Optional.empty());

        EntidadeNaoEncontradoException entidadeNaoEncontradoException = assertThrows(
                EntidadeNaoEncontradoException.class,
                () -> atualizarMinhasCredenciaisService.executar(login, atualizarMeusDadosRequest)
        );

        assertEquals("Usuário não encontrado", entidadeNaoEncontradoException.getMessage());

        verify(usuarioRepositoryPort, times(1)).buscarPorLogin(login.valor());
        verify(usuarioRepositoryPort, never()).salvar(any());
    }
}
