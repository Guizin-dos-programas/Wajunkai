package com.wajunkai.sistemaEstoque.infrastructure.web.controller;

import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.ExportarBalancoPdfUsecase;
import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.ObterBalancoDeCompraUsecase;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.BalancoComprasResponse;
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
public class RelatorioBalancoController {

    private final ObterBalancoDeCompraUsecase obterBalancoDeCompraUsecase;
    private final ExportarBalancoPdfUsecase exportarBalancoPdfUsecase;

    public RelatorioBalancoController(ObterBalancoDeCompraUsecase obterBalancoDeCompraUsecase, ExportarBalancoPdfUsecase exportarBalancoPdfUsecase) {
        this.obterBalancoDeCompraUsecase = obterBalancoDeCompraUsecase;
        this.exportarBalancoPdfUsecase = exportarBalancoPdfUsecase;
    }

    @GetMapping
    public ResponseEntity<BalancoComprasResponse> obterBalanco(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim){

        BalancoComprasResponse response = obterBalancoDeCompraUsecase.executar(dataInicio, dataFim);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/balanco/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
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
