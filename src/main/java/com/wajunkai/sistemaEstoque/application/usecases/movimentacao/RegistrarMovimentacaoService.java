package com.wajunkai.sistemaEstoque.application.usecases.movimentacao;

import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.RegistrarMovimentacaoUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.MovimentacaoRepositoryPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.RegistrarMovimentacaoRequest;
import org.springframework.stereotype.Service;


@Service
public class RegistrarMovimentacaoService implements RegistrarMovimentacaoUsecase {

    private final MovimentacaoRepositoryPort movimentacaoRepositoryPort;
    private final ProdutoRepositoryPort produtoRepositoryPort;

    public RegistrarMovimentacaoService(MovimentacaoRepositoryPort movimentacaoRepositoryPort, ProdutoRepositoryPort produtoRepositoryPort) {
        this.movimentacaoRepositoryPort = movimentacaoRepositoryPort;
        this.produtoRepositoryPort = produtoRepositoryPort;
    }

    @Override
    public Movimentacao executar(RegistrarMovimentacaoRequest request) {

        Produto produto = produtoRepositoryPort.buscarPorId(request.produtoId())
                .orElseThrow(()-> new EntidadeNaoEncontradoException("Produto não encontrado"));

        System.out.println(">>> SITUACAO DO PRODUTO CARREGADO: " + produto.getSituacao());
        System.out.println(">>> IS ATIVO? " + produto.isAtivo());

        QuantidadeEstoque qtdAtualizada = new QuantidadeEstoque(request.quantidade());

        produto.aplicarMovimentacao(request.tipoMovimentacao(), qtdAtualizada);
        produtoRepositoryPort.salvar(produto);

        Movimentacao movimentacao = new Movimentacao(
                null,
                produto,
                request.usuarioId(),
                request.tipoMovimentacao(),
                qtdAtualizada,
                null,
                request.doadorNome(),
                request.cidade(),
                request.valorCompra(),
                request.residenteNome()
        );

        return movimentacaoRepositoryPort.salvar(movimentacao);
    }
}
