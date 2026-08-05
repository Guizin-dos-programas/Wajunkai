package com.wajunkai.sistemaEstoque.infrastructure.persistence.adapter;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.wajunkai.sistemaEstoque.application.ports.outbound.GerarRelatorioPdfPort;
import com.wajunkai.sistemaEstoque.domain.exceptions.RegraDeNegocioException;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.BalancoComprasResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class RelatorioPdfAdapter implements GerarRelatorioPdfPort {

    private static final NumberFormat MOEDA_BRL = NumberFormat.getCurrencyInstance();
    @Override
    public byte[] gerarBalancoPdf(BalancoComprasResponse dados) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            Paragraph title = new Paragraph("Relatório de Balanço Financeiro")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String periodoText = String.format("Período: %s até %s",
                    dados.dataInicio().format(formatter),
                    dados.dataFinal().format(formatter));

            document.add(new Paragraph(periodoText)
                    .setFontSize(11)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(15));

            Table tabelaGeral = new Table(UnitValue.createPercentArray(new float[]{60, 40}))
                    .useAllAvailableWidth()
                    .setMarginBottom(20);

            addHeaderCell(tabelaGeral, "Métrica Geral");
            addHeaderCell(tabelaGeral, "Valor / Quantidade");

            addRow(tabelaGeral, "Total Gasto em Compras", formatarMoeda(dados.valorTotalGasto()));
            addRow(tabelaGeral, "Itens Comprados", String.valueOf(dados.totalRegistrosCompra()));

            document.add(tabelaGeral);

            if (dados.gastoPorCategoria() != null && !dados.gastoPorCategoria().isEmpty()) {

                Paragraph subTitulo = new Paragraph("Detalhamento de Gastos por Categoria")
                        .setFontSize(14)
                        .setBold()
                        .setMarginBottom(10);
                document.add(subTitulo);

                Table tabelaCategorias = new Table(UnitValue.createPercentArray(new float[]{60, 40}))
                        .useAllAvailableWidth();

                addHeaderCell(tabelaCategorias, "Categoria");
                addHeaderCell(tabelaCategorias, "Valor Total Gasto");

                for (BalancoComprasResponse.ItemBalancoCategoria item : dados.gastoPorCategoria()) {
                    String nomeCategoria = formatarNomeCategoria(item.categoria());
                    String valorFormatado = formatarMoeda(item.valorTotal());

                    addRow(tabelaCategorias, nomeCategoria, valorFormatado);
                }

                document.add(tabelaCategorias);
            }

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao gerar PDF do relatório: " + e.getMessage());
        }
    }

    private String formatarMoeda(BigDecimal valor) {
        return MOEDA_BRL.format(Objects.requireNonNullElse(valor, BigDecimal.ZERO));
    }

    private String formatarNomeCategoria(String categoria) {
        if (categoria == null) return "-";
        return categoria.substring(0, 1).toUpperCase() + categoria.substring(1).toLowerCase();
    }

    private void addHeaderCell(Table table, String text) {
        Cell cell = new Cell()
                .add(new Paragraph(text).setBold().setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(ColorConstants.DARK_GRAY)
                .setTextAlignment(TextAlignment.LEFT);
        table.addHeaderCell(cell);
    }

    private void addRow(Table table, String label, String value) {
        table.addCell(new Cell().add(new Paragraph(label)));
        table.addCell(new Cell().add(new Paragraph(value)));
    }
}