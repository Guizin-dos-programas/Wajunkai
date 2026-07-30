package com.wajunkai.sistemaEstoque.infrastructure.security.config;

import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.RegistrarMovimentacaoUsecase;
import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.*;
import com.wajunkai.sistemaEstoque.application.ports.inbound.usuario.*;
import com.wajunkai.sistemaEstoque.application.ports.outbound.MovimentacaoRepositoryPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.application.usecases.movimentacao.RegistrarMovimentacaoService;
import com.wajunkai.sistemaEstoque.application.usecases.produto.*;
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

    @Bean
    DesativarProdutoUsecase desativarProdutoUsecase(
            ProdutoRepositoryPort produtoRepositoryPort
    ){
        return new DesativarProdutoService(produtoRepositoryPort);
    }

    @Bean
    AtivarProdutoUsecase ativarProdutoUsecase(
            ProdutoRepositoryPort produtoRepositoryPort
    ){
        return new AtivarProdutoService(produtoRepositoryPort);
    }

    @Bean
    RegistrarMovimentacaoUsecase registrarMovimentacaoUsecase(
            MovimentacaoRepositoryPort movimentacaoRepositoryPort,
            ProdutoRepositoryPort produtoRepositoryPort
    ){
        return new RegistrarMovimentacaoService(movimentacaoRepositoryPort, produtoRepositoryPort);
    }
}