package com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.usuario;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "O email é obrigatório") String login,
        @NotBlank(message = "A senha é obrigatória") String senha

) {}
