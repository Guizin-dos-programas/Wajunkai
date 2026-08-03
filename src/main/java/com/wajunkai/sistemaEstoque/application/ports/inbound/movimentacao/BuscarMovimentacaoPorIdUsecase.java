package com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao;

import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.MovimentacaoResponse;

public interface BuscarMovimentacaoPorIdUsecase {

    MovimentacaoResponse executar(Long id);
}
