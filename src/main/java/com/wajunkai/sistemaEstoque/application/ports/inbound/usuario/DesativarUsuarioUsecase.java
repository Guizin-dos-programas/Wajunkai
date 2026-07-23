package com.wajunkai.sistemaEstoque.application.ports.inbound.usuario;

import com.wajunkai.sistemaEstoque.domain.model.Usuario;

public interface DesativarUsuarioUsecase {
    Usuario executar(Long id);
}
