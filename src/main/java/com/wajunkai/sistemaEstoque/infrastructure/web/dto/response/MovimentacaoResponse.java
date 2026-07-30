package com.wajunkai.sistemaEstoque.infrastructure.web.dto.response;

import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoMovimentacao;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimentacaoResponse(

        Long id,
        Long produtoId,
        String produtoNome,
        Long usuarioId,
        TipoMovimentacao tipoMovimentacao,
        BigDecimal quantidade,
        LocalDateTime dataHora,
        String doadorNome,
        String cidade,
        BigDecimal valorCompra,
        String residenteNome
) {
    public static MovimentacaoResponse fromDomain(Movimentacao domain) {
        return new MovimentacaoResponse(
                domain.getId(),
                domain.getProduto().getId(),
                domain.getProduto().getNome(),
                domain.getUsuarioId(),
                domain.getTipoMovimentacao(),
                domain.getQuantidade().valor(),
                domain.getDataHora(),
                domain.getDoadorNome(),
                domain.getCidade(),
                domain.getValorCompra(),
                domain.getResidenteNome()
        );
    }
}
