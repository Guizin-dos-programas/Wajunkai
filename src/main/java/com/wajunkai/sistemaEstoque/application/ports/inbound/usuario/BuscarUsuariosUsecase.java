package com.wajunkai.sistemaEstoque.application.ports.inbound.usuario;

import com.wajunkai.sistemaEstoque.application.dtos.PaginaResultado;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;

public interface BuscarUsuariosUsecase {
    PaginaResultado<Usuario> executar(int pagina, int tamanho);
}
