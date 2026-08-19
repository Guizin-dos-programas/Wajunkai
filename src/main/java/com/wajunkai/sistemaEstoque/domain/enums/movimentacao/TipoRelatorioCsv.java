package com.wajunkai.sistemaEstoque.domain.enums.movimentacao;

import com.wajunkai.sistemaEstoque.domain.model.ColunaCsv;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

public enum TipoRelatorioCsv {

    DOACOES(List.of(
            coluna("Produto", movimentacao -> movimentacao.getProduto().getNome()),
            coluna("Quantidade", movimentacao -> {
                BigDecimal qtd = movimentacao.getQuantidade().valor(); // ou o método que retorna o BigDecimal
                if (qtd == null) return "0";

                return qtd.stripTrailingZeros()
                        .toPlainString()
                        .replace(".", ",");
            }),
            coluna("Nome doador", Movimentacao::getDoadorNome),
            coluna("Cidade", Movimentacao::getCidade),
            coluna("Responsável", movimentacao -> movimentacao.getUsuarioId().toString()),
            coluna("Data/hora", Movimentacao::getDataHoraFormatada)
    )),

    COMPRAS(List.of(
            coluna("Produto", movimentacao -> movimentacao.getProduto().getNome()),
            coluna("Quantidade", movimentacao -> {
                BigDecimal qtd = movimentacao.getQuantidade().valor(); // ou o método que retorna o BigDecimal
                if (qtd == null) return "0";

                return qtd.stripTrailingZeros()
                        .toPlainString()
                        .replace(".", ",");
            }),
            coluna("Valor compra", movimentacao -> movimentacao.getValorCompra().toString()),
            coluna("Responsável", movimentacao -> movimentacao.getUsuarioId().toString()),
            coluna("Data/hora", Movimentacao::getDataHoraFormatada)
    )),

    SAIDA_RESIDENTE(List.of(
            coluna("Produto", movimentacao -> movimentacao.getProduto().getNome()),
            coluna("Quantidade", movimentacao -> {
                BigDecimal qtd = movimentacao.getQuantidade().valor(); // ou o método que retorna o BigDecimal
                if (qtd == null) return "0";

                return qtd.stripTrailingZeros()
                        .toPlainString()
                        .replace(".", ",");
            }),
            coluna("Residente", Movimentacao::getResidenteNome),
            coluna("Responsável", movimentacao -> movimentacao.getUsuarioId().toString()),
            coluna("Data/hora", Movimentacao::getDataHoraFormatada)
    )),

    SAIDA_PERDA(List.of(
            coluna("Produto", movimentacao -> movimentacao.getProduto().getNome()),
            coluna("Quantidade", movimentacao -> {
                BigDecimal qtd = movimentacao.getQuantidade().valor(); // ou o método que retorna o BigDecimal
                if (qtd == null) return "0";

                return qtd.stripTrailingZeros()
                        .toPlainString()
                        .replace(".", ",");
            }),
            coluna("Responsável", movimentacao -> movimentacao.getUsuarioId().toString()),
            coluna("Data/hora", Movimentacao::getDataHoraFormatada)
    )),

    GERAL(List.of(
            coluna("ID", movimentacao -> movimentacao.getId().toString()),
            coluna("Produto", movimentacao -> movimentacao.getProduto().getNome()),
            coluna("Tipo", movimentacao -> movimentacao.getTipoMovimentacao().name()),
            coluna("Quantidade", movimentacao -> {
                BigDecimal qtd = movimentacao.getQuantidade().valor(); // ou o método que retorna o BigDecimal
                if (qtd == null) return "0";

                return qtd.stripTrailingZeros()
                        .toPlainString()
                        .replace(".", ",");
            }),
            coluna("Nome doador", Movimentacao::getDoadorNome),
            coluna("Cidade", Movimentacao::getCidade),
            coluna("Residente", Movimentacao::getResidenteNome),
            coluna("Valor compra", movimentacao -> movimentacao.getValorCompra().toString()),
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

    private static ColunaCsv coluna(String header, Function<Movimentacao, String> extrator) {
        return new ColunaCsv(header, extrator);
    }
}
