package com.wajunkai.sistemaEstoque.infrastructure.security.config;

import com.wajunkai.sistemaEstoque.application.ports.inbound.usuario.*;
import com.wajunkai.sistemaEstoque.application.usecases.*;
import com.wajunkai.sistemaEstoque.application.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
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

    @Bean
    public AtualizarUsuarioUsecase atualizarUsuarioUseCase(
            UsuarioRepositoryPort usuarioRepositoryPort,
            PasswordEncoderPort passwordEncoderPort) {

        return new AtualizarUsuarioService(usuarioRepositoryPort, passwordEncoderPort);
    }

    @Bean
    public BuscarUsuarioPorLoginUsecase buscarUsuarioPorLoginUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new BuscarUsuarioPorLoginService(usuarioRepositoryPort);
    }

    @Bean
    public BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new BuscarUsuarioPorIdService(usuarioRepositoryPort);
    }

    @Bean
    public BuscarUsuariosUsecase buscarUsuariosUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new BuscarUsuariosService(usuarioRepositoryPort);
    }

    @Bean
    public DesativarUsuarioUsecase desativarUsuarioUsecase(
            UsuarioRepositoryPort usuarioRepositoryPort
    ){
        return new DesativarUsuarioService(usuarioRepositoryPort);
    }

}