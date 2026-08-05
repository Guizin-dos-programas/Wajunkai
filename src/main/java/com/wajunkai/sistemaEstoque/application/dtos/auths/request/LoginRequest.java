package com.wajunkai.sistemaEstoque.application.dtos.auths.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "O email é obrigatório") String login,
        @NotBlank(message = "A senha é obrigatória") String senha

) {}
