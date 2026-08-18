package com.wajunkai.sistemaEstoque.application.ports.inbound.usuario;

import com.wajunkai.sistemaEstoque.domain.model.Usuario;

import java.time.LocalDate;

public interface AtualizarUsuarioUsecase {
    Usuario executar(Long id, String nomeAtualizado, String novaSenha, String telefone, LocalDate dataNascimento);
}
