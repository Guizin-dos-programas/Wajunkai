package com.wajunkai.sistemaEstoque.infrastructure.web.controller;

import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.usuario.LoginRequest;
import com.wajunkai.sistemaEstoque.application.ports.inbound.autenticacao.AtualizarTokenUsecase;
import com.wajunkai.sistemaEstoque.application.ports.inbound.autenticacao.AutenticarUsuarioUsecase;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.usuario.RefreshTokenRequest;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
@Tag(name = "Autenticação", description = "Endpoints para gerenciamento de sessão e login de usuários")
public class AuthController {

    private final AutenticarUsuarioUsecase autenticarUsuarioUsecase;
    private final AtualizarTokenUsecase atualizarTokenUsecase;

    public AuthController(AutenticarUsuarioUsecase autenticarUsuarioUsecase, AtualizarTokenUsecase atualizarTokenUsecase) {
        this.autenticarUsuarioUsecase = autenticarUsuarioUsecase;
        this.atualizarTokenUsecase = atualizarTokenUsecase;
    }

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica o usuário no sistema e retorna um token JWT de acesso.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
    })
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest){
        TokenResponse tokenResponse = autenticarUsuarioUsecase.executar(loginRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh token", description = "Atualiza token do usuario logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refresh realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
    })
    public ResponseEntity<TokenResponse> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        TokenResponse response = atualizarTokenUsecase.executar(request.refreshToken());
        return ResponseEntity.ok(response);
    }
}
