package com.wajunkai.sistemaEstoque.infrastructure.persistence.entity;

import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoMovimentacao;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoJpaEntity produto;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimentacao", nullable = false)
    @ColumnTransformer(write = "?::tipo_movimentacao_enum")
    private TipoMovimentacao tipoMovimentacao;

    @Column(name = "quantidade", nullable = false, precision = 10, scale = 3)
    private BigDecimal quantidade;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "doador_nome", length = 100)
    private String doadorNome;

    @Column(name = "cidade", length = 50)
    private String cidade;

    @Column(name = "valor_compra", precision = 10, scale = 2)
    private BigDecimal valorCompra;

    @Column(name = "residente_nome", length = 100)
    private String residenteNome;
}
