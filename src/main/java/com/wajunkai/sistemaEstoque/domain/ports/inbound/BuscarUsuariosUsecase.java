package com.wajunkai.sistemaEstoque.domain.ports.inbound;

import com.wajunkai.sistemaEstoque.application.dtos.PaginaResultado;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;

public interface BuscarUsuariosUsecase {
    PaginaResultado<Usuario> executar(int pagina, int tamanho);
}
