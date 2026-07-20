package com.wajunkai.sistemaEstoque.infrastructure.security.config;

import com.wajunkai.sistemaEstoque.application.usecases.CadastrarUsuarioService;
import com.wajunkai.sistemaEstoque.domain.ports.inbound.CadastrarUsuarioUseCase;
import com.wajunkai.sistemaEstoque.domain.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.domain.ports.outbound.UsuarioRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CadastrarUsuarioUseCase cadastrarUsuarioUseCase(
            UsuarioRepositoryPort usuarioRepositoryPort,
            PasswordEncoderPort passwordEncoderPort) {

        return new CadastrarUsuarioService(usuarioRepositoryPort, passwordEncoderPort);
    }
}