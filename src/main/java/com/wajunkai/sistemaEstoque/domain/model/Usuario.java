package com.wajunkai.sistemaEstoque.domain.model;

import com.wajunkai.sistemaEstoque.domain.enums.usuario.TipoUsuario;
import com.wajunkai.sistemaEstoque.domain.valueObject.Login;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Usuario {

    private Long id;
    private String nome;
    private Login login;
    private String senha;
    private TipoUsuario tipoUsuario;
    private boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDate dataNascimento;
    private String telefone;

    public Usuario(){}

    public Usuario(String nome, Login login, String senha, TipoUsuario tipoUsuario, LocalDate dataNascimento, String telefone) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.ativo = true;
        this.dataCadastro = LocalDateTime.now();
    }

    public Usuario(Long id, String nome, Login login, String senha, TipoUsuario tipoUsuario, LocalDate dataNascimento, String telefone, boolean ativo, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
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
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public String getTelefone() {
        return telefone;
    }

    public void atualizarDadosPessoais(String novoNome, String novaSenhaCriptografada, String telefone, LocalDate dataNascimento) {

        if (novoNome != null && !novoNome.isBlank()) this.nome = novoNome;

        if (novaSenhaCriptografada != null && !novaSenhaCriptografada.isBlank()) this.senha = novaSenhaCriptografada;

        if(telefone!= null && !telefone.isBlank()) this.telefone = telefone;

        if(dataNascimento!= null) this.dataNascimento = dataNascimento;
    }

    public void desativarUsuario(){
        this.ativo = false;
    }
}