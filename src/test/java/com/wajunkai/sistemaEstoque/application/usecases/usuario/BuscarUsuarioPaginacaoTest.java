package com.wajunkai.sistemaEstoque.application.usecases.usuario;

import com.wajunkai.sistemaEstoque.application.dtos.usuario.PaginaResultado;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.usuario.TipoUsuario;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.domain.valueObject.Login;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarUsuarioPaginacaoTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepositoryPort;

    @InjectMocks
    private BuscarUsuarioService buscarUsuarioService;

    @Test
    @DisplayName("Retorna usuários com paginação")
    void deveRetornarUsuariosPaginados(){

        Usuario usuario1 = new Usuario(
                1L,
                "João",
                new Login("joao", null),
                "123",
                TipoUsuario.FUNCIONARIO,
                LocalDate.of(2007, 3, 29),
                "44998576541",
                true,
                LocalDateTime.now()
        );

        Usuario usuario2 = new Usuario(
                2L,
                "Maria",
                new Login("maria", null),
                "123",
                TipoUsuario.FUNCIONARIO,
                LocalDate.of(2007, 3, 29),
                "44998576541",
                true,
                LocalDateTime.now()
        );


        PaginaResultado<Usuario> usuarioPage = new PaginaResultado<>(
                List.of(usuario1, usuario2),
                0,
                5,
                50,
                10
        );

        when(usuarioRepositoryPort.buscarTodosPaginado(0, 10)).thenReturn(usuarioPage);

        PaginaResultado<Usuario> resultado = buscarUsuarioService.executar(0, 10);
        assertNotNull(resultado);
        assertEquals(usuario1, resultado.conteudo().get(0));
        assertEquals(usuario2, resultado.conteudo().get(1));
        assertEquals(2, resultado.conteudo().size());
        assertEquals(5, resultado.tamanhoPagina());
        assertEquals(50, resultado.totalElementos());
        assertEquals(10, resultado.totalPaginas());

        verify(usuarioRepositoryPort).buscarTodosPaginado(0, 10);
    }
}
