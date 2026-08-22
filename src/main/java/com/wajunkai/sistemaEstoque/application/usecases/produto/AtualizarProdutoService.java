package com.wajunkai.sistemaEstoque.application.usecases.produto;

import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.AtualizarProdutoUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.produto.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.enums.produto.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeJaCadastradaException;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class AtualizarProdutoService implements AtualizarProdutoUsecase {

    private final ProdutoRepositoryPort produtoRepositoryPort;

    public AtualizarProdutoService(ProdutoRepositoryPort produtoRepositoryPort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
    }

    @Override
    public Produto executar(Long id, String nome, BigDecimal estoqueMinimo, UnidadeMedidaProduto unidadeMedida, CategoriaProduto categoria, LocalDate dataValidade, Situacao situacao) {
        Produto produtoExistente = produtoRepositoryPort.buscarPorId(id).orElseThrow(
                ()-> new EntidadeNaoEncontradoException("Produto não encontrado"));

        if (!produtoExistente.getNome().equalsIgnoreCase(nome)
                && produtoRepositoryPort.existePorNome(nome)) {
            throw new EntidadeJaCadastradaException("Já existe outro produto cadastrado com o nome: " + nome);
        }

        produtoExistente.atualizarDados(
                nome,
                QuantidadeEstoque.opcional(estoqueMinimo).orElse(null),
                unidadeMedida,
                categoria,
                dataValidade,
                situacao

        );

        return produtoRepositoryPort.salvar(produtoExistente);
    }
}
