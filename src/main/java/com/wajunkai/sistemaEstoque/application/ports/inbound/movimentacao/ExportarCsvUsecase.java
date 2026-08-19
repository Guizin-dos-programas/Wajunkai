package com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao;

import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoRelatorioCsv;

import java.time.LocalDateTime;

public interface ExportarCsvUsecase {

    byte[] executar(TipoRelatorioCsv tipo, LocalDateTime inici, LocalDateTime fim);
}
