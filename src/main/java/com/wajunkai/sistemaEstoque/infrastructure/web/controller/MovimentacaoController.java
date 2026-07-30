package com.wajunkai.sistemaEstoque.infrastructure.web.controller;

import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.RegistrarMovimentacaoUsecase;
import com.wajunkai.sistemaEstoque.application.usecases.movimentacao.RegistrarMovimentacaoService;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.RegistrarMovimentacaoRequest;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.MovimentacaoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/movimentacao")
public class MovimentacaoController {

    private final RegistrarMovimentacaoUsecase registrarMovimentacaoUsecase;

    public MovimentacaoController(RegistrarMovimentacaoUsecase registrarMovimentacaoUsecase) {
        this.registrarMovimentacaoUsecase = registrarMovimentacaoUsecase;
    }

    @PostMapping()
    public ResponseEntity<MovimentacaoResponse> registrar(@RequestBody @Valid RegistrarMovimentacaoRequest request){
        Movimentacao movimentacao = registrarMovimentacaoUsecase.executar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MovimentacaoResponse.fromDomain(movimentacao));
    }
}
