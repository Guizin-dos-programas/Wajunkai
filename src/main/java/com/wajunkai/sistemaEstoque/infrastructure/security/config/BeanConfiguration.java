package com.wajunkai.sistemaEstoque.infrastructure.security.config;

import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.AtualizarProdutoUsecase;
import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.BuscarPorIdUsecase;
import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.CadastrarProdutoUsecase;
import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.ListarProdutosUsecase;
import com.wajunkai.sistemaEstoque.application.ports.inbound.usuario.*;
import com.wajunkai.sistemaEstoque.application.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.application.usecases.produto.AtualizarProdutoService;
import com.wajunkai.sistemaEstoque.application.usecases.produto.BuscarPorIdService;
import com.wajunkai.sistemaEstoque.application.usecases.produto.CadastrarProdutoService;
import com.wajunkai.sistemaEstoque.application.usecases.produto.ListarProdutosService;
import com.wajunkai.sistemaEstoque.application.usecases.usuario.*;
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

    @Bean
    public CadastrarProdutoUsecase cadastrarProdutoUsecase(
            ProdutoRepositoryPort produtoRepositoryPort
    ){
        return new CadastrarProdutoService(produtoRepositoryPort);
    }

    @Bean
    public AtualizarProdutoUsecase atualizarProdutoUsecase(
            ProdutoRepositoryPort produtoRepositoryPort
    ){
        return new AtualizarProdutoService(produtoRepositoryPort);
    }

    @Bean
    BuscarPorIdUsecase buscarPorIdUsecase(
            ProdutoRepositoryPort produtoRepositoryPort
    ){
        return new BuscarPorIdService(produtoRepositoryPort);
    }

    @Bean
    ListarProdutosUsecase listarProdutosUsecase(
            ProdutoRepositoryPort produtoRepositoryPort
    ){
        return new ListarProdutosService(produtoRepositoryPort);
    }
}