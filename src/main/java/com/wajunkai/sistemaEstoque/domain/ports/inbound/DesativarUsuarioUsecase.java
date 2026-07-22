package com.wajunkai.sistemaEstoque.domain.ports.inbound;

import com.wajunkai.sistemaEstoque.domain.model.Usuario;

public interface DesativarUsuarioUsecase {
    Usuario executar(Long id);
}
