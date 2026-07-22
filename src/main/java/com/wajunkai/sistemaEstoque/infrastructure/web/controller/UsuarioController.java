package com.wajunkai.sistemaEstoque.infrastructure.web.controller;

import com.wajunkai.sistemaEstoque.application.dtos.PaginaResultado;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.domain.ports.inbound.*;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.AtualizarUsuarioRequest;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.CadastrarUsuarioRequest;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.UsuarioResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/usuarios")
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
    public ResponseEntity<UsuarioResponse> buscarPorLogin(@RequestHeader("X-Usuario-Login") String loginDoUsuarioLogado) {

        Usuario usuario = buscarUsuarioPorLoginUsecase.executar(loginDoUsuarioLogado);
        return ResponseEntity.ok(UsuarioResponse.fromDomain(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        Usuario usuario = buscarUsuarioPorIdUseCase.executar(id);
        return ResponseEntity.ok(UsuarioResponse.fromDomain(usuario));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody AtualizarUsuarioRequest request) {
        Usuario usuarioAtualizado = atualizarUsuarioUsecase.executar(
                id,
                request.nomeAtualizado(),
                request.senhaAtualizada()
        );

        return ResponseEntity.ok(UsuarioResponse.fromDomain(usuarioAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponse> desativarUsuario(@PathVariable Long id){
        desativarUsuarioUsecase.executar(id);
        return  ResponseEntity.noContent().build();
    }
}