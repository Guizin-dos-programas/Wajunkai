package com.wajunkai.sistemaEstoque.domain.model;

import com.wajunkai.sistemaEstoque.domain.enums.produto.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.enums.produto.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;

import java.time.LocalDate;

public class Produto {

    private Long id;
    private String nome;
    private QuantidadeEstoque quantidadeAtual;
    private QuantidadeEstoque estoqueMinimo;
    private UnidadeMedidaProduto unidadeMedidaProduto;
    private CategoriaProduto categoriaProduto;
    private LocalDate dataValidade;
    private Situacao situacao;

    public Produto(){}

    public Produto(String nome, QuantidadeEstoque quantidadeAtual, QuantidadeEstoque estoqueMinimo, UnidadeMedidaProduto unidadeMedidaProduto, CategoriaProduto categoriaProduto, LocalDate dataValidade, Situacao situacao) {

        if (quantidadeAtual != null && unidadeMedidaProduto != null) {
            quantidadeAtual.validarUnidadeMedida(unidadeMedidaProduto);
        }

        this.nome = nome;
        this.quantidadeAtual = quantidadeAtual;
        this.estoqueMinimo = estoqueMinimo;
        this.unidadeMedidaProduto = unidadeMedidaProduto;
        this.categoriaProduto = categoriaProduto;
        this.dataValidade = dataValidade;
        this.situacao = situacao != null ? situacao : Situacao.ATIVO;
    }

    public Produto(Long id, String nome, QuantidadeEstoque quantidadeAtual, QuantidadeEstoque estoqueMinimo, UnidadeMedidaProduto unidadeMedidaProduto, CategoriaProduto categoriaProduto, LocalDate dataValidade, Situacao situacao) {

        if (quantidadeAtual != null && unidadeMedidaProduto != null) {
            quantidadeAtual.validarUnidadeMedida(unidadeMedidaProduto);
        }

        this.id = id;
        this.nome = nome;
        this.quantidadeAtual = quantidadeAtual;
        this.estoqueMinimo = estoqueMinimo;
        this.unidadeMedidaProduto = unidadeMedidaProduto;
        this.categoriaProduto = categoriaProduto;
        this.dataValidade = dataValidade;
        this.situacao = situacao != null ? situacao : Situacao.ATIVO;
    }

    public void atualizarDados(
            String novoNome,
            QuantidadeEstoque novoEstoqueMinimo,
            UnidadeMedidaProduto novaUnidade,
            CategoriaProduto novaCategoria,
            LocalDate novaValidade
    ) {

        if (this.quantidadeAtual != null && novaUnidade != null) {
            this.quantidadeAtual.validarUnidadeMedida(novaUnidade);
        }

        this.nome = novoNome;
        this.estoqueMinimo = novoEstoqueMinimo;
        this.unidadeMedidaProduto = novaUnidade;
        this.categoriaProduto = novaCategoria;
        this.dataValidade = novaValidade;
    }

    public boolean precisaReposicao(){
        return quantidadeAtual.eMenorQue(estoqueMinimo);
    }

    public boolean estaVencido(){
        return dataValidade.isBefore(LocalDate.now());
    }

    public void inativarProduto(){
        this.situacao = Situacao.INATIVO;
    }

    public void ativarProduto(){
        this.situacao = Situacao.ATIVO;
    }

    public boolean isAtivo(){
        return Situacao.ATIVO.equals(this.situacao);
    }
    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public QuantidadeEstoque getQuantidadeAtual() {
        return quantidadeAtual;
    }
    public QuantidadeEstoque getEstoqueMinimo() {
        return estoqueMinimo;
    }
    public UnidadeMedidaProduto getUnidadeMedidaProduto() {
        return unidadeMedidaProduto;
    }
    public CategoriaProduto getCategoriaProduto() {
        return categoriaProduto;
    }
    public LocalDate getDataValidade() {
        return dataValidade;
    }
    public Situacao getSituacao() {return situacao;}
}
