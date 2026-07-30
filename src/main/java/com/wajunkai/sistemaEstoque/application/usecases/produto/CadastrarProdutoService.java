package com.wajunkai.sistemaEstoque.application.usecases.produto;

import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.CadastrarProdutoUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.produto.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.enums.produto.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeJaCadastradaException;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CadastrarProdutoService implements CadastrarProdutoUsecase {

    private final ProdutoRepositoryPort produtoRepositoryPort;

    public CadastrarProdutoService(ProdutoRepositoryPort produtoRepositoryPort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
    }

    @Override
    public Produto executar(String nome, BigDecimal quantidadeAtual, BigDecimal estoqueMinimo, UnidadeMedidaProduto unidadeMedidaProduto, CategoriaProduto categoria, LocalDate dataValidade, Situacao situacao) {

        if(produtoRepositoryPort.existePorNome(nome)){
            throw new EntidadeJaCadastradaException("Produto: " + nome + " já cadastrado!");
        }

        QuantidadeEstoque estoqueMinimoVO = new QuantidadeEstoque(estoqueMinimo);
        QuantidadeEstoque quantidadeAtualVo = new QuantidadeEstoque(quantidadeAtual);

        Produto produto = new Produto(nome, quantidadeAtualVo, estoqueMinimoVO, unidadeMedidaProduto, categoria, dataValidade, situacao);

        return produtoRepositoryPort.salvar(produto);
    }
}
