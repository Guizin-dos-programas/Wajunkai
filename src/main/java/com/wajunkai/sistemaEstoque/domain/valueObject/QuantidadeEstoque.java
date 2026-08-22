package com.wajunkai.sistemaEstoque.domain.valueObject;

import com.wajunkai.sistemaEstoque.domain.enums.produto.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.exceptions.RegraDeNegocioException;

import java.math.BigDecimal;
import java.util.Optional;

public record QuantidadeEstoque(BigDecimal valor) {

    public QuantidadeEstoque {
        if (valor != null && valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraDeNegocioException("A quantidade em estoque não pode ser negativa.");
        }
    }

    public static QuantidadeEstoque obrigatorio(BigDecimal valor) {
        if (valor == null) {
            throw new RegraDeNegocioException("A quantidade em estoque não pode ser nula.");
        }
        return new QuantidadeEstoque(valor);
    }

    public static Optional<QuantidadeEstoque> opcional(BigDecimal valor) {
        if (valor == null) return Optional.empty();
        return Optional.of(new QuantidadeEstoque(valor));
    }

    public void validarUnidadeMedida(UnidadeMedidaProduto unidade) {
        if (!unidade.isFracionavel() && valor.stripTrailingZeros().scale() > 0) {
            throw new RegraDeNegocioException("A unidade " + unidade.getSigla() + " não permite valores fracionados.");
        }
    }

    public boolean eMenorQue(QuantidadeEstoque outra) {
        return this.valor.compareTo(outra.valor()) < 0;
    }
}
