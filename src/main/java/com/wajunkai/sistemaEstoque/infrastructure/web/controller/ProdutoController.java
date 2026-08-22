package com.wajunkai.sistemaEstoque.infrastructure.web.controller;

import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaQuery;
import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaResultado;
import com.wajunkai.sistemaEstoque.application.ports.inbound.produto.*;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.AtualizarProdutoRequest;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.CadastrarProdutoRequest;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.ProdutoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/produtos")
@Tag(name = "Produtos", description = "Gerenciamento dos produtos do estoque")
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
    @Operation(
            summary = "Cadastrar produto",
            description = "Realiza o cadastro de um novo produto no estoque."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "409", description = "Já existe um produto com o mesmo nome")})
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
    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza as informações de um produto."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoResponse> atualizarProduto(@PathVariable Long id, @Valid @RequestBody AtualizarProdutoRequest atualizarProdutoRequest){
        Produto produtoAtualizado = atualizarProdutoUsecase.executar(
                id,
                atualizarProdutoRequest.nome(),
                atualizarProdutoRequest.estoqueMinimo(),
                atualizarProdutoRequest.unidadeMedida(),
                atualizarProdutoRequest.categoria(),
                atualizarProdutoRequest.dataValidade(),
                atualizarProdutoRequest.situacao()
        );

        return ResponseEntity.ok(ProdutoResponse.fromDomain(produtoAtualizado));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna um produto pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable Long id){
        Produto produtoId = buscarPorIdUsecase.executar(id);
        return ResponseEntity.ok(ProdutoResponse.fromDomain(produtoId));
    }

    @GetMapping
    @Operation(
            summary = "Listar produtos",
            description = "Lista os produtos cadastrados com paginação e filtro opcional por situação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso")
    })
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
    @Operation(
            summary = "Desativar produto",
            description = "Realiza a desativação lógica de um produto."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> desativarProduto(@PathVariable Long id){
        desativarProdutoUsecase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    @Operation(
            summary = "Ativar produto",
            description = "Ativa novamente um produto desativado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> ativarProduto(@PathVariable Long id){
        ativarProdutoUsecase.executar(id);
        return ResponseEntity.noContent().build();
    }
}
