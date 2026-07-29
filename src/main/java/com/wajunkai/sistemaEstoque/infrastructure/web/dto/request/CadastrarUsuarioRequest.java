package com.wajunkai.sistemaEstoque.infrastructure.web.dto.request;

import com.wajunkai.sistemaEstoque.domain.enums.usuario.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CadastrarUsuarioRequest(

        @NotBlank(message = "O nome é obrigatório.")
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome deve conter apenas letras.")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        String nome,

        @NotBlank(message = "O identificador de login (CPF, E-mail ou Username) é obrigatório.")
        String login,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        String senha,

        @NotNull(message = "O tipo do usuário é obrigatório.")
        TipoUsuario tipoUsuario
) {
}
