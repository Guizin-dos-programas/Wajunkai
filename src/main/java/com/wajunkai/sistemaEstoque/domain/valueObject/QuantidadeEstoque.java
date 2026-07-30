package com.wajunkai.sistemaEstoque.domain.valueObject;

import com.wajunkai.sistemaEstoque.domain.enums.produto.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.exceptions.RegraDeNegocioException;

import java.math.BigDecimal;

public record QuantidadeEstoque(BigDecimal valor) {

    public QuantidadeEstoque {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraDeNegocioException("A quantidade em estoque não pode ser nula ou negativa.");
        }
    }

    public void validarUnidadeMedida(UnidadeMedidaProduto unidade) {
        if (!unidade.isFracionavel() && valor.stripTrailingZeros().scale() > 0) {
            throw new RegraDeNegocioException("A unidade " + unidade.getSigla() + " não permite valores fracionados.");
        }
    }

    public QuantidadeEstoque somar(BigDecimal quantidade) {
        return new QuantidadeEstoque(this.valor.add(quantidade));
    }

    public QuantidadeEstoque subtrair(BigDecimal quantidade) {
        return new QuantidadeEstoque(this.valor.subtract(quantidade));
    }

    public boolean eMenorQue(QuantidadeEstoque outra) {
        return this.valor.compareTo(outra.valor()) < 0;
    }
}
