package com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao;

import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoRelatorioCsv;

import java.time.Instant;

public interface ExportarCsvUsecase {

    byte[] executar(TipoRelatorioCsv tipo, Instant inicio, Instant fim);
}
