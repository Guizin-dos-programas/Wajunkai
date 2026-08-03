package com.wajunkai.sistemaEstoque.infrastructure.web.controller;

import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.ObterBalancoDeCompraUsecase;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.BalancoComprasResponse;
import org.springframework.format.annotation.DateTimeFormat;
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

    public RelatorioBalancoController(ObterBalancoDeCompraUsecase obterBalancoDeCompraUsecase) {
        this.obterBalancoDeCompraUsecase = obterBalancoDeCompraUsecase;
    }

    @GetMapping
    public ResponseEntity<BalancoComprasResponse> obterBalanco(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim){

        BalancoComprasResponse response = obterBalancoDeCompraUsecase.executar(dataInicio, dataFim);
        return ResponseEntity.ok(response);
    }
}
