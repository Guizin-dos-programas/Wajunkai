package com.wajunkai.sistemaEstoque.application.usecases.produto;

import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.AtualizarProdutoUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.produto.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.produto.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradaException;
import com.wajunkai.sistemaEstoque.domain.exceptions.ProdutoJaCadastradoException;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AtualizarProdutoService implements AtualizarProdutoUsecase {

    private final ProdutoRepositoryPort produtoRepositoryPort;

    public AtualizarProdutoService(ProdutoRepositoryPort produtoRepositoryPort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
    }

    @Override
    public Produto executar(Long id, String nome, BigDecimal estoqueMinimo, UnidadeMedidaProduto unidadeMedida, CategoriaProduto categoria, LocalDate dataValidade) {
        Produto produtoExistente = produtoRepositoryPort.buscarPorId(id).orElseThrow(
                ()-> new EntidadeNaoEncontradaException("Entidade não encontrada"));

        if (!produtoExistente.getNome().equalsIgnoreCase(nome)
                && produtoRepositoryPort.existePorNome(nome)) {
            throw new ProdutoJaCadastradoException("Já existe outro produto cadastrado com o nome: " + nome);
        }

        QuantidadeEstoque novoEstoqueMinimo = new QuantidadeEstoque(estoqueMinimo);
        produtoExistente.atualizarDados(
                nome,
                novoEstoqueMinimo,
                unidadeMedida,
                categoria,
                dataValidade
        );

        return produtoRepositoryPort.salvar(produtoExistente);
    }
}
