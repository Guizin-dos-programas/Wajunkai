package com.wajunkai.sistemaEstoque.application.ports.inbound.usuario;

import com.wajunkai.sistemaEstoque.domain.enums.usuario.TipoUsuario;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;

import java.time.LocalDate;

public interface CadastrarUsuarioUseCase {
    Usuario executar(String nome, String loginBruto, String senhaLimpa, TipoUsuario tipoUsuario, String telefone, LocalDate dataNascimento);
}
