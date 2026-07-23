package com.wajunkai.sistemaEstoque.domain.model;

import com.wajunkai.sistemaEstoque.domain.enums.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.exceptions.DataValidadeInvalidaException;
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

    public Produto(){}

    public Produto(Long id, String nome, QuantidadeEstoque quantidadeAtual, QuantidadeEstoque estoqueMinimo, UnidadeMedidaProduto unidadeMedidaProduto, CategoriaProduto categoriaProduto, LocalDate dataValidade) {

        if (quantidadeAtual != null && unidadeMedidaProduto != null) {
            quantidadeAtual.validarUnidadeMedida(unidadeMedidaProduto);
        }

        if(dataValidade.isBefore(LocalDate.now())){
            throw new DataValidadeInvalidaException("Data de validade inválida");
        }

        this.id = id;
        this.nome = nome;
        this.quantidadeAtual = quantidadeAtual;
        this.estoqueMinimo = estoqueMinimo;
        this.unidadeMedidaProduto = unidadeMedidaProduto;
        this.categoriaProduto = categoriaProduto;
        this.dataValidade = dataValidade;

    }

    public boolean precisaDeReposicao() {
        return this.quantidadeAtual.eMenorQue(this.estoqueMinimo);
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

    public boolean estaVencido() {
        if (this.dataValidade == null) {
            return false;
        }
        return LocalDate.now().isAfter(this.dataValidade);
    }

}
