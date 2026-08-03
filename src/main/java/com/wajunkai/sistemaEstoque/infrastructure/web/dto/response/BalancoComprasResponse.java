package com.wajunkai.sistemaEstoque.infrastructure.web.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BalancoComprasResponse(

        LocalDate dataInicio,
        LocalDate dataFinal,
        BigDecimal valorTotalGasto,
        Long totalRegistrosCompra,
        List<ItemBalancoCategoria> gastoPorCategoria
        ) {
    public record ItemBalancoCategoria(
            String categoria,
            BigDecimal valorTotal
    ){

    }
}
