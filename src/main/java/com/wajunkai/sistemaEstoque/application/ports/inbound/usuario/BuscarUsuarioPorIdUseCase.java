package com.wajunkai.sistemaEstoque.application.ports.inbound.usuario;

import com.wajunkai.sistemaEstoque.domain.model.Usuario;

public interface BuscarUsuarioPorIdUseCase {
    Usuario executar(Long id);
}
