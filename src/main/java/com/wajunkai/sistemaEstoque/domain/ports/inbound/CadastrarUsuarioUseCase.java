package com.wajunkai.sistemaEstoque.domain.ports.inbound;

import com.wajunkai.sistemaEstoque.domain.enums.TipoUsuario;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;

public interface CadastrarUsuarioUseCase {
    Usuario executar(String nome, String loginBruto, String senhaLimpa, TipoUsuario tipoUsuario);
}
