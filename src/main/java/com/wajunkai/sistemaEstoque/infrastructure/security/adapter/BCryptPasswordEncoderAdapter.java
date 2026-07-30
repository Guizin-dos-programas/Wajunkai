package com.wajunkai.sistemaEstoque.infrastructure.security.adapter;

import com.wajunkai.sistemaEstoque.application.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.domain.exceptions.RegraDeNegocioException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoder springPasswordEncoder;

    public BCryptPasswordEncoderAdapter(PasswordEncoder springPasswordEncoder) {
        this.springPasswordEncoder = springPasswordEncoder;
    }

    @Override
    public String encode(String senhaLimpa) {
        if (senhaLimpa == null || senhaLimpa.isBlank()) {
            throw new RegraDeNegocioException("A senha não pode ser nula ou vazia.");
        }
        return springPasswordEncoder.encode(senhaLimpa);
    }

    @Override
    public boolean matches(String senhaLimpa, String hashSalvo) {
        return springPasswordEncoder.matches(senhaLimpa, hashSalvo);
    }


}