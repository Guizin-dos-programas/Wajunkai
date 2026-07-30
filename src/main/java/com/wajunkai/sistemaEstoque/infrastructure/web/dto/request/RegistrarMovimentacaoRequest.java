package com.wajunkai.sistemaEstoque.infrastructure.web.dto.request;

import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoMovimentacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RegistrarMovimentacaoRequest(

        @NotNull(message = "O ID do produto é obrigatório.")
        Long produtoId,

        @NotNull(message = "O ID do usuário é obrigatório.")
        Long usuarioId,

        @NotNull(message = "O tipo de movimentação é obrigatório.")
        TipoMovimentacao tipoMovimentacao,

        @NotNull(message = "A quantidade é obrigatória.")
        @Positive(message = "A quantidade deve ser positiva.") BigDecimal quantidade,

        String doadorNome,
        String cidade,
        BigDecimal valorCompra,
        String residenteNome

) {
}
