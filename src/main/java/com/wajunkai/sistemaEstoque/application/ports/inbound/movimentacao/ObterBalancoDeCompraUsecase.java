package com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao;

import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.BalancoComprasResponse;

import java.time.LocalDate;

public interface ObterBalancoDeCompraUsecase {

    BalancoComprasResponse executar(LocalDate inicio, LocalDate fim);
}
