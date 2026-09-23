package com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.usuario;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "O Refresh Token é obrigatório")
        String refreshToken
) {
}
