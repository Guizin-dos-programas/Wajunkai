package com.wajunkai.sistemaEstoque.domain.model;

import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoMovimentacao;
import com.wajunkai.sistemaEstoque.domain.exceptions.RegraDeNegocioException;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Movimentacao {

    private Long id;
    private Produto produto;
    private Long usuarioId;
    private TipoMovimentacao tipoMovimentacao;
    private QuantidadeEstoque quantidade;
    private Instant dataHora;
    private String doadorNome;
    private String cidade;
    private BigDecimal valorCompra;
    private String residenteNome;
    private LocalDate dataValidade;

    public Movimentacao(Long id, Produto produto, Long usuarioId, TipoMovimentacao tipoMovimentacao, QuantidadeEstoque quantidade, Instant dataHora, String doadorNome, String cidade, BigDecimal valorCompra, String residenteNome, LocalDate dataValidade) {

        validar(produto, usuarioId, tipoMovimentacao, quantidade, valorCompra, residenteNome, dataValidade);

        this.id = id;
        this.produto = produto;
        this.usuarioId = usuarioId;
        this.tipoMovimentacao = tipoMovimentacao;
        this.quantidade = quantidade;
        this.dataHora = Instant.now();
        this.doadorNome = doadorNome;
        this.cidade = cidade;
        this.valorCompra = valorCompra;
        this.residenteNome = residenteNome;
        this.dataValidade = dataValidade;
    }

    public void validar(Produto produto, Long usuarioId, TipoMovimentacao tipoMovimentacao,
                        QuantidadeEstoque quantidade, BigDecimal valorCompra, String residenteNome, LocalDate dataValidade){

        if (produto == null) throw new RegraDeNegocioException("A movimentação precisa estar vinculada a um produto");

        if (!produto.isAtivo()) throw new RegraDeNegocioException("Não é possível realizar movimentação para um produto inativo.");

        if (usuarioId == null) throw new RegraDeNegocioException("O usuário responsável pela movimentação é obrigatório para auditoria.");

        if (tipoMovimentacao == null) throw new RegraDeNegocioException("O tipo de movimentação é obrigatório.");

        if (quantidade == null || quantidade.valor().compareTo(BigDecimal.ZERO) <= 0) throw new RegraDeNegocioException("A quantidade movimentada deve ser maior que zero.");

        if (dataValidade != null && dataValidade.isBefore(LocalDate.now())) {
            throw new RegraDeNegocioException(
                    "A data de validade não pode estar no passado."
            );
        }

        if (TipoMovimentacao.ENTRADA_COMPRA.equals(tipoMovimentacao) && (valorCompra == null || valorCompra.compareTo(BigDecimal.ZERO) <= 0)) throw new RegraDeNegocioException("Para movimentações de compra, o valor de compra deve ser informado e maior que zero.");

        if (TipoMovimentacao.SAIDA_CONSUMO.equals(tipoMovimentacao) && residenteNome != null && residenteNome.isBlank()) throw new RegraDeNegocioException("O nome do residente, quando informado, não pode estar em branco.");

    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public QuantidadeEstoque getQuantidade() {
        return quantidade;
    }

    public Instant getDataHora() {
        return dataHora;
    }

    public String getDoadorNome() {
        return doadorNome;
    }

    public String getCidade() {
        return cidade;
    }

    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    public String getResidenteNome() {
        return residenteNome;
    }

    public LocalDate getDataValidade() {return dataValidade;
    }

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public String getDataHoraFormatada() {
        return dataHora != null
                ? dataHora.atZone(ZoneId.of("America/Sao_Paulo"))
                .format(FORMATO_DATA)
                : "";
    }

}
