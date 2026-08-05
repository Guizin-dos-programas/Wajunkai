package com.wajunkai.sistemaEstoque.infrastructure.web.controller;

import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.ExportarBalancoPdfUsecase;
import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.ObterBalancoDeCompraUsecase;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.BalancoComprasResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/relatorio")
@Tag(name = "Relatórios", description = "Operações relacionadas aos relatórios do sistema")
public class RelatorioBalancoController {

    private final ObterBalancoDeCompraUsecase obterBalancoDeCompraUsecase;
    private final ExportarBalancoPdfUsecase exportarBalancoPdfUsecase;

    public RelatorioBalancoController(ObterBalancoDeCompraUsecase obterBalancoDeCompraUsecase, ExportarBalancoPdfUsecase exportarBalancoPdfUsecase) {
        this.obterBalancoDeCompraUsecase = obterBalancoDeCompraUsecase;
        this.exportarBalancoPdfUsecase = exportarBalancoPdfUsecase;
    }

    @GetMapping
    @Operation(
            summary = "Consultar balanço de compras",
            description = "Retorna o balanço de compras dentro de um período informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balanço obtido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Período informado é inválido")
    })
    public ResponseEntity<BalancoComprasResponse> obterBalanco(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim){

        BalancoComprasResponse response = obterBalancoDeCompraUsecase.executar(dataInicio, dataFim);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/balanco/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(
            summary = "Exportar balanço em PDF",
            description = "Gera um relatório em PDF contendo o balanço de compras do período informado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "PDF gerado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Período informado é inválido"
            )
    })
    public ResponseEntity<byte[]> exportarBalancoPdf(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        byte[] pdfBytes = exportarBalancoPdfUsecase.executar(dataInicio, dataFim);

        String filename = String.format("balanco_%s_%s.pdf", dataInicio, dataFim);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
