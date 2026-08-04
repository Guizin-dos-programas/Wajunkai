package com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao;

import java.time.LocalDate;

public interface ExportarBalancoPdfUsecase {
    byte [] executar(LocalDate dataInicio, LocalDate dataFim);
}
