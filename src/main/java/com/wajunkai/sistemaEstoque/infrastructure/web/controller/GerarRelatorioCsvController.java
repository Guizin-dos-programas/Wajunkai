package com.wajunkai.sistemaEstoque.infrastructure.web.controller;

import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.ExportarCsvUsecase;
import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoRelatorioCsv;
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
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("v1/relatorios/movimentacoes")
@Tag(name = "Relatório", description = "Endpoint para geração de relatórios")
public class GerarRelatorioCsvController {

    private final ExportarCsvUsecase exportarCsvUsecase;

    public GerarRelatorioCsvController(ExportarCsvUsecase exportarCsvUsecase) {
        this.exportarCsvUsecase = exportarCsvUsecase;
    }

    @GetMapping("/csv")
    @Operation(summary = "Gerar relatório csv", description = "Gera relatório em csv, com base no tipo, data de inicio e data final")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
    })
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
