package com.wajunkai.sistemaEstoque.infrastructure.web.controller;

import com.wajunkai.sistemaEstoque.application.dtos.usuario.PaginaResultado;
import com.wajunkai.sistemaEstoque.application.ports.inbound.usuario.*;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.AtualizarUsuarioRequest;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.CadastrarUsuarioRequest;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.UsuarioResponse;
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
@RequestMapping("/v1/usuarios")
@Tag(name = "Usuários", description = "Operações relacionadas ao gerenciamento de usuários")
public class UsuarioController {

    private final CadastrarUsuarioUseCase cadastrarUsuarioUseCase;
    private final BuscarUsuarioPorLoginUsecase buscarUsuarioPorLoginUsecase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final BuscarUsuariosUsecase buscarUsuariosUsecase;
    private final AtualizarUsuarioUsecase atualizarUsuarioUsecase;
    private final DesativarUsuarioUsecase desativarUsuarioUsecase;

    public UsuarioController(CadastrarUsuarioUseCase cadastrarUsuarioUseCase, BuscarUsuarioPorLoginUsecase buscarUsuarioPorLoginUsecase, BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase, BuscarUsuariosUsecase buscarUsuariosUsecase, AtualizarUsuarioUsecase atualizarUsuarioUsecase, DesativarUsuarioUsecase desativarUsuarioUsecase) {
        this.cadastrarUsuarioUseCase = cadastrarUsuarioUseCase;
        this.buscarUsuarioPorLoginUsecase = buscarUsuarioPorLoginUsecase;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
        this.buscarUsuariosUsecase = buscarUsuariosUsecase;
        this.atualizarUsuarioUsecase = atualizarUsuarioUsecase;
        this.desativarUsuarioUsecase = desativarUsuarioUsecase;
    }

    @PostMapping
    @Operation(
            summary = "Cadastrar usuário",
            description = "Realiza o cadastro de um novo usuário no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "409", description = "Já existe um usuário com este login")
    })
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody CadastrarUsuarioRequest request) {

        Usuario usuarioCriado = cadastrarUsuarioUseCase.executar(
                request.nome(),
                request.login(),
                request.senha(),
                request.tipoUsuario()
        );

        UsuarioResponse response = UsuarioResponse.fromDomain(usuarioCriado);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Listar usuários",
            description = "Lista os usuários cadastrados de forma paginada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso")
    })
    public ResponseEntity<PaginaResultado<UsuarioResponse>> listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {

        PaginaResultado<Usuario> resultadoDominio = buscarUsuariosUsecase.executar(pagina, tamanho);

        List<UsuarioResponse> responses = resultadoDominio.conteudo()
                .stream()
                .map(UsuarioResponse::fromDomain)
                .toList();

        PaginaResultado<UsuarioResponse> respostaPaginada = new PaginaResultado<>(
                responses,
                resultadoDominio.paginaAtual(),
                resultadoDominio.tamanhoPagina(),
                resultadoDominio.totalElementos(),
                resultadoDominio.totalPaginas()
        );

        return ResponseEntity.ok(respostaPaginada);
    }

    @GetMapping("/me")
    @Operation(
            summary = "Buscar usuário autenticado",
            description = "Retorna os dados do usuário autenticado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    public ResponseEntity<UsuarioResponse> buscarPorLogin(@RequestHeader("X-Usuario-Login") String loginDoUsuarioLogado) {

        Usuario usuario = buscarUsuarioPorLoginUsecase.executar(loginDoUsuarioLogado);
        return ResponseEntity.ok(UsuarioResponse.fromDomain(usuario));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna um usuário pelo identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        Usuario usuario = buscarUsuarioPorIdUseCase.executar(id);
        return ResponseEntity.ok(UsuarioResponse.fromDomain(usuario));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza as informações do usuário."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody AtualizarUsuarioRequest request) {
        Usuario usuarioAtualizado = atualizarUsuarioUsecase.executar(
                id,
                request.nomeAtualizado(),
                request.senhaAtualizada()
        );

        return ResponseEntity.ok(UsuarioResponse.fromDomain(usuarioAtualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Desativar usuário",
            description = "Realiza a desativação lógica de um usuário."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponse> desativarUsuario(@PathVariable Long id){
        desativarUsuarioUsecase.executar(id);
        return  ResponseEntity.noContent().build();
    }
}