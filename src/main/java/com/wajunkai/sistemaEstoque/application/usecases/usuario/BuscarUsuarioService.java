package com.wajunkai.sistemaEstoque.application.usecases.usuario;

import com.wajunkai.sistemaEstoque.application.dtos.usuario.PaginaResultado;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.application.ports.inbound.usuario.BuscarUsuariosUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class BuscarUsuarioService implements BuscarUsuariosUsecase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public BuscarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public PaginaResultado<Usuario> executar(int pagina, int tamanho) {
        return usuarioRepositoryPort.buscarTodosPaginado(pagina, tamanho);
    }
}
