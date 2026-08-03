package com.wajunkai.sistemaEstoque.infrastructure.web.controller;

import com.wajunkai.sistemaEstoque.application.dtos.movimentacao.PaginaQueryMovimentacao;
import com.wajunkai.sistemaEstoque.application.dtos.movimentacao.PaginaResultadoMovimentacao;
import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaQuery;
import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaResultado;
import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.BuscarMovimentacaoPorIdUsecase;
import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.ListarMovimentacaoUsecase;
import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.RegistrarMovimentacaoUsecase;
import com.wajunkai.sistemaEstoque.application.usecases.movimentacao.RegistrarMovimentacaoService;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.RegistrarMovimentacaoRequest;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.MovimentacaoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/movimentacao")
public class MovimentacaoController {

    private final RegistrarMovimentacaoUsecase registrarMovimentacaoUsecase;
    private final ListarMovimentacaoUsecase listarMovimentacaoUsecase;
    private final BuscarMovimentacaoPorIdUsecase buscarMovimentacaoPorIdUsecase;

    public MovimentacaoController(RegistrarMovimentacaoUsecase registrarMovimentacaoUsecase, ListarMovimentacaoUsecase listarMovimentacaoUsecase, BuscarMovimentacaoPorIdUsecase buscarMovimentacaoPorIdUsecase) {
        this.registrarMovimentacaoUsecase = registrarMovimentacaoUsecase;
        this.listarMovimentacaoUsecase = listarMovimentacaoUsecase;
        this.buscarMovimentacaoPorIdUsecase = buscarMovimentacaoPorIdUsecase;
    }

    @PostMapping()
    public ResponseEntity<MovimentacaoResponse> registrar(@RequestBody @Valid RegistrarMovimentacaoRequest request){
        Movimentacao movimentacao = registrarMovimentacaoUsecase.executar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MovimentacaoResponse.fromDomain(movimentacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(buscarMovimentacaoPorIdUsecase.executar(id));
    }

    @GetMapping
    public ResponseEntity<PaginaResultadoMovimentacao<MovimentacaoResponse>> listar(
            @RequestParam(required = false) Long produtoId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int tamanho) {

        PaginaQueryMovimentacao query = new PaginaQueryMovimentacao(pagina, tamanho);
        PaginaResultadoMovimentacao<MovimentacaoResponse> resultado = listarMovimentacaoUsecase.executar(produtoId, query);

        return ResponseEntity.ok(resultado);
    }
}
