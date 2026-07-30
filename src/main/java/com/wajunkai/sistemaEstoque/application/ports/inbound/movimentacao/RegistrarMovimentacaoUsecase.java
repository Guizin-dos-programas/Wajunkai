package com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao;

import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.RegistrarMovimentacaoRequest;

public interface RegistrarMovimentacaoUsecase {

    Movimentacao executar(RegistrarMovimentacaoRequest request);
}
