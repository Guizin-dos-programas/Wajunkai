package com.wajunkai.sistemaEstoque.infrastructure.web.controller;

import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaQuery;
import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaResultado;
import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.*;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.AtualizarProdutoRequest;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.CadastrarProdutoRequest;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.ProdutoResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/produtos")
public class ProdutoController {

    private final CadastrarProdutoUsecase cadastrarProdutoUsecase;
    private final AtualizarProdutoUsecase atualizarProdutoUsecase;
    private final BuscarPorIdUsecase buscarPorIdUsecase;
    private final ListarProdutosUsecase listarProdutosUsecase;
    private final DesativarProdutoUsecase desativarProdutoUsecase;
    private final AtivarProdutoUsecase ativarProdutoUsecase;

    public ProdutoController(CadastrarProdutoUsecase cadastrarProdutoUsecase, AtualizarProdutoUsecase atualizarProdutoUsecase, BuscarPorIdUsecase buscarPorIdUsecase, ListarProdutosUsecase listarProdutosUsecase, DesativarProdutoUsecase desativarProdutoUsecase, AtivarProdutoUsecase ativarProdutoUsecase) {
        this.cadastrarProdutoUsecase = cadastrarProdutoUsecase;
        this.atualizarProdutoUsecase = atualizarProdutoUsecase;
        this.buscarPorIdUsecase = buscarPorIdUsecase;
        this.listarProdutosUsecase = listarProdutosUsecase;
        this.desativarProdutoUsecase = desativarProdutoUsecase;
        this.ativarProdutoUsecase = ativarProdutoUsecase;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> cadastrar(@Valid @RequestBody CadastrarProdutoRequest cadastrarProdutoRequest){

        Produto produto = cadastrarProdutoUsecase.executar(
                cadastrarProdutoRequest.nome(),
                cadastrarProdutoRequest.quantidadeAtual(),
                cadastrarProdutoRequest.estoqueMinimo(),
                cadastrarProdutoRequest.unidadeMedida(),
                cadastrarProdutoRequest.categoria(),
                cadastrarProdutoRequest.dataValidade(),
                cadastrarProdutoRequest.situacao()
        );

        ProdutoResponse response = ProdutoResponse.fromDomain(produto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizarProduto(@Valid @RequestBody AtualizarProdutoRequest atualizarProdutoRequest){
        Produto produtoAtualizado = atualizarProdutoUsecase.executar(
                atualizarProdutoRequest.id(),
                atualizarProdutoRequest.nome(),
                atualizarProdutoRequest.estoqueMinimo(),
                atualizarProdutoRequest.unidadeMedida(),
                atualizarProdutoRequest.categoria(),
                atualizarProdutoRequest.dataValidade()
        );

        return ResponseEntity.ok(ProdutoResponse.fromDomain(produtoAtualizado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable Long id){
        Produto produtoId = buscarPorIdUsecase.executar(id);
        return ResponseEntity.ok(ProdutoResponse.fromDomain(produtoId));
    }

    @GetMapping
    public ResponseEntity<PaginaResultado<ProdutoResponse>> listarTodos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho,
            @RequestParam(required = false) Situacao situacao
            ){
        PaginaQuery paginaQuery = new PaginaQuery(pagina, tamanho, situacao);
        PaginaResultado<Produto> paginaResultado = listarProdutosUsecase.executar(paginaQuery, situacao);

        List<ProdutoResponse> response = paginaResultado.conteudo().stream()
                .map(ProdutoResponse::fromDomain).toList();

        PaginaResultado<ProdutoResponse> responsePaginaResultado = new PaginaResultado<>(
                response,
                paginaResultado.paginaAtual(),
                paginaResultado.tamanhoPagina(),
                paginaResultado.totalElementos(),
                paginaResultado.totalPaginas()
        );

        return ResponseEntity.ok(responsePaginaResultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativarProduto(@PathVariable Long id){
        desativarProdutoUsecase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativarProduto(@PathVariable Long id){
        ativarProdutoUsecase.executar(id);
        return ResponseEntity.noContent().build();
    }
}
