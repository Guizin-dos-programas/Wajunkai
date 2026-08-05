package com.wajunkai.sistemaEstoque.infrastructure.web.controller;

import com.wajunkai.sistemaEstoque.application.dtos.auths.request.LoginRequest;
import com.wajunkai.sistemaEstoque.application.dtos.auths.response.TokenResponse;
import com.wajunkai.sistemaEstoque.application.ports.inbound.autenticacao.AutenticarUsuarioUsecase;
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

    public AuthController(AutenticarUsuarioUsecase autenticarUsuarioUsecase) {
        this.autenticarUsuarioUsecase = autenticarUsuarioUsecase;
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
}
