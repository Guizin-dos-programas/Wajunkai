package com.wajunkai.sistemaEstoque.domain.ports.inbound;

import com.wajunkai.sistemaEstoque.domain.model.Usuario;

public interface BuscarUsuarioPorIdUseCase {
    Usuario executar(Long id);
}
