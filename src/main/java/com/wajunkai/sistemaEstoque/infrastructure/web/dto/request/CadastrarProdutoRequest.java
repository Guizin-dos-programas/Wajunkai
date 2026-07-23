package com.wajunkai.sistemaEstoque.infrastructure.web.dto.request;

import com.wajunkai.sistemaEstoque.domain.enums.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.UnidadeMedidaProduto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CadastrarProdutoRequest(

        @NotBlank(message = "O nome do produto é obrigatório.")
        @Size(min = 3, max = 100, message = "O nome do produto deve ter entre 3 e 100 caracteres.")
        String nome,

        @NotNull(message = "A quantidade inicial é obrigatória.")
        @PositiveOrZero(message = "A quantidade inicial não pode ser negativa.")
        BigDecimal quantidadeAtual,

        @NotNull(message = "O estoque mínimo é obrigatório.")
        @PositiveOrZero(message = "O estoque mínimo não pode ser negativo.")
        BigDecimal estoqueMinimo,

        @NotNull(message = "A unidade de medida é obrigatória.")
        UnidadeMedidaProduto unidadeMedida,

        @NotNull(message = "A categoria do produto é obrigatória.")
        CategoriaProduto categoria,

        @FutureOrPresent(message = "A data de validade não pode ser uma data passada.")
        LocalDate dataValidade
) {
}
