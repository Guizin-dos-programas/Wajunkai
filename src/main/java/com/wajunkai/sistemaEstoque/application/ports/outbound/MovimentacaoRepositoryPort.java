package com.wajunkai.sistemaEstoque.application.ports.outbound;

import com.wajunkai.sistemaEstoque.application.dtos.movimentacao.PaginaQueryMovimentacao;
import com.wajunkai.sistemaEstoque.application.dtos.movimentacao.PaginaResultadoMovimentacao;
import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoRelatorioCsv;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovimentacaoRepositoryPort {

    Movimentacao salvar(Movimentacao movimentacao);
    PaginaResultadoMovimentacao<Movimentacao> buscarPorProduto(Long produtoId, PaginaQueryMovimentacao query);
    Optional<Movimentacao> buscarPorId(Long id);
    PaginaResultadoMovimentacao<Movimentacao> buscarTodas(PaginaQueryMovimentacao query);
    List<Movimentacao> buscarPorPeriodoETipo(LocalDateTime inicio, LocalDateTime fim, TipoRelatorioCsv tipoRelatorio);
}
