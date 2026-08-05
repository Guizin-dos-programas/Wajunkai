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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/movimentacao")
@Tag(name = "Movimentação", description = "Endpoint para registro e visualização das movimentações no estoque")
public class MovimentacaoController {

    private final RegistrarMovimentacaoUsecase registrarMovimentacaoUsecase;
    private final ListarMovimentacaoUsecase listarMovimentacaoUsecase;
    private final BuscarMovimentacaoPorIdUsecase buscarMovimentacaoPorIdUsecase;

    public MovimentacaoController(RegistrarMovimentacaoUsecase registrarMovimentacaoUsecase, ListarMovimentacaoUsecase listarMovimentacaoUsecase, BuscarMovimentacaoPorIdUsecase buscarMovimentacaoPorIdUsecase) {
        this.registrarMovimentacaoUsecase = registrarMovimentacaoUsecase;
        this.listarMovimentacaoUsecase = listarMovimentacaoUsecase;
        this.buscarMovimentacaoPorIdUsecase = buscarMovimentacaoPorIdUsecase;
    }

    @PostMapping
    @Operation(summary = "Registrar movimentação", description = "Registra a movimentacao (entrada/saida) dos produtos no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimentação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<MovimentacaoResponse> registrar(@RequestBody @Valid RegistrarMovimentacaoRequest request){
        Movimentacao movimentacao = registrarMovimentacaoUsecase.executar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MovimentacaoResponse.fromDomain(movimentacao));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar movimentação por id", description = "Busca a movimentação com base no id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimentação encontrada"),
            @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    })
    public ResponseEntity<MovimentacaoResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(buscarMovimentacaoPorIdUsecase.executar(id));
    }

    @GetMapping
    @Operation(summary = "Listar  movimentações", description = "Lista todas as movimentações com paginação e filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimentações listadas com sucesso")
    })
    public ResponseEntity<PaginaResultadoMovimentacao<MovimentacaoResponse>> listar(
            @RequestParam(required = false) Long produtoId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int tamanho) {

        PaginaQueryMovimentacao query = new PaginaQueryMovimentacao(pagina, tamanho);
        PaginaResultadoMovimentacao<MovimentacaoResponse> resultado = listarMovimentacaoUsecase.executar(produtoId, query);

        return ResponseEntity.ok(resultado);
    }
}
