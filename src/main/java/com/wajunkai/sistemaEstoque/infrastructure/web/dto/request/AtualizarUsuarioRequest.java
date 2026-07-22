package com.wajunkai.sistemaEstoque.infrastructure.web.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AtualizarUsuarioRequest(

        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome deve conter apenas letras.")
        String nomeAtualizado,

        @Size(min = 6, message = "A nova senha deve ter no mínimo 6 caracteres.")
        String senhaAtualizada
) {
}
