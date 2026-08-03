package com.wajunkai.sistemaEstoque.application.usecases.movimentacao;

import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.BuscarMovimentacaoPorIdUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.MovimentacaoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.MovimentacaoResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class BuscarMovimentacaoPorIdService implements BuscarMovimentacaoPorIdUsecase {

    private final MovimentacaoRepositoryPort movimentacaoRepositoryPort;

    public BuscarMovimentacaoPorIdService(MovimentacaoRepositoryPort movimentacaoRepositoryPort) {
        this.movimentacaoRepositoryPort = movimentacaoRepositoryPort;
    }

    @Override
    @Transactional
    public MovimentacaoResponse executar(Long id) {
        return movimentacaoRepositoryPort.buscarPorId(id)
                .map(MovimentacaoResponse::fromDomain).
                orElseThrow(()-> new EntidadeNaoEncontradoException("Movimentacao não encontrada"));
    }
}
