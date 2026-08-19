package com.wajunkai.sistemaEstoque.infrastructure.web.controller;

import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.ExportarCsvUsecase;
import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoRelatorioCsv;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("v1/relatorios/movimentacoes")
public class GerarRelatorioCsvController {

    private final ExportarCsvUsecase exportarCsvUsecase;

    public GerarRelatorioCsvController(ExportarCsvUsecase exportarCsvUsecase) {
        this.exportarCsvUsecase = exportarCsvUsecase;
    }

    @GetMapping("/csv")
    public ResponseEntity<byte[]> gerarRelatorio(@RequestParam TipoRelatorioCsv tipo,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim){

        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(LocalTime.MAX);

        byte [] csv = exportarCsvUsecase.executar(tipo, inicio, fim);
        String nomeArquivo = String.format("relatorio_%s_%s.csv", tipo.name().toLowerCase(), LocalDate.now());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv; charset=UTF-8"));
        headers.setContentDispositionFormData("attachment", nomeArquivo);

        return ResponseEntity.ok().headers(headers).body(csv);
    }
}
