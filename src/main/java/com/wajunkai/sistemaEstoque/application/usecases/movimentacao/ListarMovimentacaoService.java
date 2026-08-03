package com.wajunkai.sistemaEstoque.application.usecases.movimentacao;

import com.wajunkai.sistemaEstoque.application.dtos.movimentacao.PaginaQueryMovimentacao;
import com.wajunkai.sistemaEstoque.application.dtos.movimentacao.PaginaResultadoMovimentacao;
import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.ListarMovimentacaoUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.MovimentacaoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.MovimentacaoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarMovimentacaoService implements ListarMovimentacaoUsecase {

    private final MovimentacaoRepositoryPort movimentacaoRepositoryPort;

    public ListarMovimentacaoService(MovimentacaoRepositoryPort movimentacaoRepositoryPort) {
        this.movimentacaoRepositoryPort = movimentacaoRepositoryPort;
    }

    @Override
    public PaginaResultadoMovimentacao<MovimentacaoResponse> executar(Long produtoId, PaginaQueryMovimentacao query) {
        PaginaResultadoMovimentacao<Movimentacao> resultado;

        if(produtoId != null){
            resultado = movimentacaoRepositoryPort.buscarPorProduto(produtoId, query);
        }
        else{
            resultado = movimentacaoRepositoryPort.buscarTodas(query);
        }
        List<MovimentacaoResponse> dtoSaida = resultado.conteudo().stream().map(MovimentacaoResponse::fromDomain).toList();
        return new PaginaResultadoMovimentacao<>(
                dtoSaida,
                resultado.paginaAtual(),
                resultado.tamanhoPagina(),
                resultado.totalElementos(),
                resultado.totalPaginas()
        );
    }

}
