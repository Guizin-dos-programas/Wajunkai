package com.wajunkai.sistemaEstoque.domain.model;

import com.wajunkai.sistemaEstoque.domain.enums.TipoUsuario;
import com.wajunkai.sistemaEstoque.domain.valueObject.Login;

import java.time.LocalDateTime;

public class Usuario {

    private Long id;
    private String nome;
    private Login login;
    private String senha;
    private TipoUsuario tipoUsuario;
    private boolean ativo;
    private LocalDateTime dataCadastro;

    public Usuario(String nome, Login login, String senha, TipoUsuario tipoUsuario) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.ativo = true;
        this.dataCadastro = LocalDateTime.now();
    }

    public Usuario(Long id, String nome, Login login, String senha, TipoUsuario tipoUsuario, boolean ativo, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.ativo = ativo;
        this.dataCadastro = dataCadastro;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public Login getLogin() { return login; }
    public String getSenha() { return senha; }
    public TipoUsuario getTipoUsuario() { return tipoUsuario; }
    public boolean isAtivo() { return ativo; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
}