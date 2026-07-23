package com.wajunkai.sistemaEstoque.infrastructure.persistence.entity;

import com.wajunkai.sistemaEstoque.domain.enums.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tb_produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private QuantidadeEstoque quantidadeAtual;

    @Column(nullable = false)
    private QuantidadeEstoque estoqueMinimo;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_medida", nullable = false)
    private UnidadeMedidaProduto unidadeMedidaProduto;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaProduto categoriaProduto;

    @Column(nullable = true)
    private LocalDate dataValidade;
}
