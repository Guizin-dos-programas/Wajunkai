package com.wajunkai.sistemaEstoque.domain.ports.inbound;

import com.wajunkai.sistemaEstoque.domain.model.Usuario;

public interface AtualizarUsuarioUsecase {
    Usuario executar(Long id, String nomeAtualizado, String novaSenha);
}
