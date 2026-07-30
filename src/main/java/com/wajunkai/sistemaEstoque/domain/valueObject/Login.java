package com.wajunkai.sistemaEstoque.domain.valueObject;

import com.wajunkai.sistemaEstoque.domain.enums.usuario.TipoLogin;
import com.wajunkai.sistemaEstoque.domain.exceptions.CredenciaisInvalidasException;
import com.wajunkai.sistemaEstoque.domain.exceptions.RegraDeNegocioException;

import java.util.regex.Pattern;

public record Login(String valor, TipoLogin tipoLogin) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern CPF_PATTERN = Pattern.compile("^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{4,20}$");

    public Login {
        if (valor == null || valor.isBlank()) {
            throw new CredenciaisInvalidasException("Identificador de login não pode ser vazio.");
        }

        valor = valor.trim();

        String cpfLimpo = valor.replaceAll("\\D", "");
        if (cpfLimpo.length() == 11) {
            if (!CPF_PATTERN.matcher(valor).matches()) {
                throw new RegraDeNegocioException("Formato de CPF inválido.");
            }
            valor = cpfLimpo;
            tipoLogin = TipoLogin.CPF;
        }

        else if (valor.contains("@")) {
            valor = valor.toLowerCase();
            if (!EMAIL_PATTERN.matcher(valor).matches()) {
                throw new RegraDeNegocioException("Formato de e-mail inválido.");
            }
            tipoLogin = TipoLogin.EMAIL;
        }
        else {
            if (valor.length() < 3 || valor.length() > 30) {
                throw new RegraDeNegocioException("Nome de usuário deve ter entre 3 e 30 caracteres.");
            }
            if (!USERNAME_PATTERN.matcher(valor).matches()) {
                throw new RegraDeNegocioException("Formato de username inválido.");
            }
            tipoLogin = TipoLogin.USERNAME;
        }
    }
}
