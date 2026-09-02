package com.wajunkai.sistemaEstoque.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "tb_refresh_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "data_expiracao", nullable = false)
    private Instant dataExpiracao;

    @Column(nullable = false)
    private boolean revogado = false;

    public RefreshTokenJpaEntity(String token, Long usuarioId, Instant dataExpiracao){
        this.token = token;
        this.usuarioId = usuarioId;
        this.dataExpiracao = dataExpiracao;
        this.revogado = false;
    }

}
