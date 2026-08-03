package com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao;

import com.wajunkai.sistemaEstoque.application.dtos.movimentacao.PaginaQueryMovimentacao;
import com.wajunkai.sistemaEstoque.application.dtos.movimentacao.PaginaResultadoMovimentacao;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.MovimentacaoResponse;

public interface ListarMovimentacaoUsecase {

    PaginaResultadoMovimentacao<MovimentacaoResponse> executar(Long produtoId, PaginaQueryMovimentacao query);
}
