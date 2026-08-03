package com.wajunkai.sistemaEstoque.application.ports.outbound;

import com.wajunkai.sistemaEstoque.application.dtos.movimentacao.PaginaQueryMovimentacao;
import com.wajunkai.sistemaEstoque.application.dtos.movimentacao.PaginaResultadoMovimentacao;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;

import java.util.Optional;

public interface MovimentacaoRepositoryPort {

    Movimentacao salvar(Movimentacao movimentacao);
    PaginaResultadoMovimentacao<Movimentacao> buscarPorProduto(Long produtoId, PaginaQueryMovimentacao query);
    Optional<Movimentacao> buscarPorId(Long id);
    PaginaResultadoMovimentacao<Movimentacao> buscarTodas(PaginaQueryMovimentacao query);
}
