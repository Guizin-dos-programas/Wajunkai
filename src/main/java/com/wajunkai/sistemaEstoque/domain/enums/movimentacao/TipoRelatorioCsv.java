package com.wajunkai.sistemaEstoque.domain.enums.movimentacao;

import com.wajunkai.sistemaEstoque.domain.model.ColunaCsv;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public enum TipoRelatorioCsv {

    DOACOES(List.of(
            coluna("Produto", (m, nomes) -> m.getProduto().getNome()),
            coluna("Quantidade", (m, nomes) -> formatarQuantidade(m)),
            coluna("Nome doador", (m, nomes) -> m.getDoadorNome()),
            coluna("Cidade", (m, nomes) -> m.getCidade()),
            coluna("Responsável", TipoRelatorioCsv::formatarResponsavel),
            coluna("Data/hora", (m, nomes) -> m.getDataHoraFormatada())
    )),

    COMPRAS(List.of(
            coluna("Produto", (m, nomes) -> m.getProduto().getNome()),
            coluna("Quantidade", (m, nomes) -> formatarQuantidade(m)),
            coluna("Valor compra", (m, nomes) -> formatarValorCompra(m)),
            coluna("Responsável", TipoRelatorioCsv::formatarResponsavel),
            coluna("Data/hora", (m, nomes) -> m.getDataHoraFormatada())
    )),

    SAIDA_RESIDENTE(List.of(
            coluna("Produto", (m, nomes) -> m.getProduto().getNome()),
            coluna("Quantidade", (m, nomes) -> formatarQuantidade(m)),
            coluna("Residente", (m, nomes) -> m.getResidenteNome()),
            coluna("Responsável", TipoRelatorioCsv::formatarResponsavel),
            coluna("Data/hora", (m, nomes) -> m.getDataHoraFormatada())
    )),

    SAIDA_PERDA(List.of(
            coluna("Produto", (m, nomes) -> m.getProduto().getNome()),
            coluna("Quantidade", (m, nomes) -> formatarQuantidade(m)),
            coluna("Responsável", TipoRelatorioCsv::formatarResponsavel),
            coluna("Data/hora", (m, nomes) -> m.getDataHoraFormatada())
    )),

    GERAL(List.of(
            coluna("ID", (m, nomes) -> m.getId() != null ? m.getId().toString() : ""),
            coluna("Produto", (m, nomes) -> m.getProduto().getNome()),
            coluna("Tipo", (m, nomes) -> m.getTipoMovimentacao().name()),
            coluna("Quantidade", (m, nomes) -> formatarQuantidade(m)),
            coluna("Nome doador", (m, nomes) -> m.getDoadorNome()),
            coluna("Cidade", (m, nomes) -> m.getCidade()),
            coluna("Residente", (m, nomes) -> m.getResidenteNome()),
            coluna("Valor compra", (m, nomes) -> formatarValorCompra(m)),
            coluna("Responsável", TipoRelatorioCsv::formatarResponsavel),
            coluna("Data/hora", (m, nomes) -> m.getDataHoraFormatada())
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
            BiFunction<Movimentacao, Map<Long, String>, String> extrator) {
        return new ColunaCsv(header, extrator);
    }

    private static String formatarDecimalBr(BigDecimal valor) {
        if (valor == null) {
            return "";
        }
        return valor.setScale(2, java.math.RoundingMode.HALF_UP)
                .toPlainString()
                .replace(".", ",");
    }

    private static String formatarQuantidade(Movimentacao movimentacao) {
        if (movimentacao.getQuantidade() == null) {
            return "0";
        }
        BigDecimal quantidade = movimentacao.getQuantidade().valor();
        if (quantidade == null) {
            return "0";
        }
        return formatarDecimalBr(quantidade);
    }

    private static String formatarValorCompra(Movimentacao movimentacao) {
        return formatarDecimalBr(movimentacao.getValorCompra());
    }

    private static String formatarResponsavel(Movimentacao movimentacao, Map<Long, String> nomesUsuarios) {
        Long usuarioId = movimentacao.getUsuarioId();
        if (usuarioId == null) {
            return "";
        }
        return nomesUsuarios.getOrDefault(usuarioId, "Usuário #" + usuarioId);
    }
}