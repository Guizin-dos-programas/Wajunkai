package com.wajunkai.sistemaEstoque.application.usecases;

import com.wajunkai.sistemaEstoque.application.dtos.PaginaResultado;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.application.ports.inbound.usuario.BuscarUsuariosUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;

public class BuscarUsuariosService implements BuscarUsuariosUsecase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public BuscarUsuariosService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public PaginaResultado<Usuario> executar(int pagina, int tamanho) {
        return usuarioRepositoryPort.buscarTodosPaginado(pagina, tamanho);
    }
}
