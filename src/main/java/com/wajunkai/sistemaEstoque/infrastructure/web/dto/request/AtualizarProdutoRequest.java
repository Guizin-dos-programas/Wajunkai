package com.wajunkai.sistemaEstoque.infrastructure.web.dto.request;

import com.wajunkai.sistemaEstoque.domain.enums.produto.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.enums.produto.UnidadeMedidaProduto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AtualizarProdutoRequest(


        Long id,

        @Size(min = 3, max = 100, message = "O nome do produto deve ter entre 3 e 100 caracteres.")
        String nome,

        @PositiveOrZero(message = "O estoque mínimo não pode ser negativo.")
        BigDecimal estoqueMinimo,

        UnidadeMedidaProduto unidadeMedida,

        CategoriaProduto categoria,

        @FutureOrPresent(message = "A data de validade não pode ser uma data passada.")
        LocalDate dataValidade,

        Situacao situacao
) {
}
