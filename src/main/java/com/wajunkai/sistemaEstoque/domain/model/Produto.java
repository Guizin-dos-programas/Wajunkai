package com.wajunkai.sistemaEstoque.domain.model;

import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoMovimentacao;
import com.wajunkai.sistemaEstoque.domain.enums.produto.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.enums.produto.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.exceptions.RegraDeNegocioException;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;

import java.math.BigDecimal;
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
            LocalDate novaValidade,
            Situacao situacaoAtualizada
    ) {

        if(novoNome != null && !nome.isBlank()) this.nome = novoNome;

        if(novoEstoqueMinimo != null) this.estoqueMinimo = novoEstoqueMinimo;

        if(novaUnidade !=null) this.unidadeMedidaProduto = novaUnidade;

        if(novaCategoria != null) this.categoriaProduto = novaCategoria;

        if(novaValidade != null) this.dataValidade = novaValidade;

        if(situacaoAtualizada != null) this.situacao = situacaoAtualizada;
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

    public void aplicarMovimentacao(TipoMovimentacao tipo, QuantidadeEstoque quantidadeMovimentada) {

        if (!isAtivo()) {
            throw new RegraDeNegocioException(
                    String.format("Não é possível realizar movimentações no produto '%s' porque ele está INATIVO.", this.nome)
            );
        }

        if (quantidadeMovimentada == null || quantidadeMovimentada.valor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("A quantidade da movimentação deve ser maior que zero.");
        }

        if (tipo.isEntrada()) {
            aplicarEntrada(quantidadeMovimentada);
        } else if (tipo.isSaida()) {
            aplicarSaida(quantidadeMovimentada);
        } else {
            throw new RegraDeNegocioException("Tipo de movimentação não reconhecido.");
        }
    }

    private void aplicarEntrada(QuantidadeEstoque quantidadeMovimentada) {
        BigDecimal novoSaldo = this.quantidadeAtual.valor().add(quantidadeMovimentada.valor());
        this.quantidadeAtual = new QuantidadeEstoque(novoSaldo);
    }

    private void aplicarSaida(QuantidadeEstoque quantidadeMovimentada) {

        if (this.quantidadeAtual.valor().compareTo(quantidadeMovimentada.valor()) < 0) {
            throw new RegraDeNegocioException(
                    String.format(
                            "Saldo insuficiente para o produto '%s'. Saldo atual: %s, Tentativa de saída: %s",
                            this.nome,
                            this.quantidadeAtual.valor(),
                            quantidadeMovimentada.valor()
                    )
            );
        }

        BigDecimal novoSaldo = this.quantidadeAtual.valor().subtract(quantidadeMovimentada.valor());
        this.quantidadeAtual = new QuantidadeEstoque(novoSaldo);
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
