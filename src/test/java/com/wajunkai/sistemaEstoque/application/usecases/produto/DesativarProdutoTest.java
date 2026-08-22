package com.wajunkai.sistemaEstoque.application.usecases.produto;

import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DesativarProdutoTest {

    @Mock
    private ProdutoRepositoryPort produtoRepositoryPort;

    @InjectMocks
    private DesativarProdutoService desativarProdutoService;

    @Test
    @DisplayName("Desativa produtos por exclusão logica")
    void desativaProdutos(){

        Produto produtoExistente = new Produto(
                1L,
                null,
                null,
                null,
                null,
                null,
                null,
                Situacao.ATIVO
        );
        when(produtoRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(produtoExistente));
        when(produtoRepositoryPort.salvar(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Produto produto = desativarProdutoService.executar(1L);

        assertEquals(Situacao.INATIVO, produto.getSituacao());
        verify(produtoRepositoryPort).buscarPorId(1L);
        verify(produtoRepositoryPort).salvar(any(Produto.class));
    }

    @Test
    @DisplayName("Retorna EntidadeNaoEncontradaException se id nulo ou inexistente")
    void deveLancarExcecao(){

        when(produtoRepositoryPort.buscarPorId(1L)).thenReturn(Optional.empty());

        EntidadeNaoEncontradoException entidadeNaoEncontradoException =
                assertThrows(
                        EntidadeNaoEncontradoException.class,
                        ()->desativarProdutoService.executar(1L));

        assertEquals("Produto não encontrado", entidadeNaoEncontradoException.getMessage());
        verify(produtoRepositoryPort).buscarPorId(1L);
        verify(produtoRepositoryPort, never()).salvar(any(Produto.class));
    }

}
