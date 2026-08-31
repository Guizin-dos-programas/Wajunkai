package com.wajunkai.sistemaEstoque.domain.enums.movimentacao;

import com.wajunkai.sistemaEstoque.domain.model.ColunaCsv;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

public enum TipoRelatorioCsv {

    DOACOES(List.of(
            coluna("Produto", movimentacao -> movimentacao.getProduto().getNome()),
            coluna("Quantidade", TipoRelatorioCsv::formatarQuantidade),
            coluna("Nome doador", Movimentacao::getDoadorNome),
            coluna("Cidade", Movimentacao::getCidade),
            coluna("Responsável", movimentacao -> movimentacao.getUsuarioId().toString()),
            coluna("Data/hora", Movimentacao::getDataHoraFormatada)
    )),

    COMPRAS(List.of(
            coluna("Produto", movimentacao -> movimentacao.getProduto().getNome()),
            coluna("Quantidade", TipoRelatorioCsv::formatarQuantidade),
            coluna("Valor compra", movimentacao ->
                    movimentacao.getValorCompra() != null
                            ? movimentacao.getValorCompra().toString()
                            : ""
            ),
            coluna("Responsável", movimentacao -> movimentacao.getUsuarioId().toString()),
            coluna("Data/hora", Movimentacao::getDataHoraFormatada)
    )),

    SAIDA_RESIDENTE(List.of(
            coluna("Produto", movimentacao -> movimentacao.getProduto().getNome()),
            coluna("Quantidade", TipoRelatorioCsv::formatarQuantidade),
            coluna("Residente", Movimentacao::getResidenteNome),
            coluna("Responsável", movimentacao -> movimentacao.getUsuarioId().toString()),
            coluna("Data/hora", Movimentacao::getDataHoraFormatada)
    )),

    SAIDA_PERDA(List.of(
            coluna("Produto", movimentacao -> movimentacao.getProduto().getNome()),
            coluna("Quantidade", TipoRelatorioCsv::formatarQuantidade),
            coluna("Responsável", movimentacao -> movimentacao.getUsuarioId().toString()),
            coluna("Data/hora", Movimentacao::getDataHoraFormatada)
    )),

    GERAL(List.of(
            coluna("ID", movimentacao -> movimentacao.getId().toString()),
            coluna("Produto", movimentacao -> movimentacao.getProduto().getNome()),
            coluna("Tipo", movimentacao -> movimentacao.getTipoMovimentacao().name()),
            coluna("Quantidade", TipoRelatorioCsv::formatarQuantidade),
            coluna("Nome doador", Movimentacao::getDoadorNome),
            coluna("Cidade", Movimentacao::getCidade),
            coluna("Residente", Movimentacao::getResidenteNome),
            coluna("Valor compra", movimentacao ->
                    movimentacao.getValorCompra() != null
                            ? movimentacao.getValorCompra().toString()
                            : ""
            ),
            coluna("Responsável", movimentacao -> movimentacao.getUsuarioId().toString()),
            coluna("Data/hora", Movimentacao::getDataHoraFormatada)
    ));

    private final List<ColunaCsv> colunas;

    TipoRelatorioCsv(List<ColunaCsv> colunas) {
        this.colunas = colunas;
    }

    public List<ColunaCsv> getColunas() {
        return colunas;
    }

    private static ColunaCsv coluna(
            String header,
            Function<Movimentacao, String> extrator) {

        return new ColunaCsv(header, extrator);
    }

    private static String formatarQuantidade(Movimentacao movimentacao) {
        BigDecimal quantidade = movimentacao.getQuantidade().valor();

        if (quantidade == null) {
            return "0";
        }

        return quantidade
                .stripTrailingZeros()
                .toPlainString()
                .replace(".", ",");
    }
}