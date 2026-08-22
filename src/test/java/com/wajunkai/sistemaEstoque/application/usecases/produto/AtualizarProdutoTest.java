package com.wajunkai.sistemaEstoque.application.usecases.produto;

import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.produto.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.enums.produto.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtualizarProdutoTest {

    @Mock
    private ProdutoRepositoryPort produtoRepositoryPort;

    @InjectMocks
    private AtualizarProdutoService atualizarProdutoService;

    @Test
    @DisplayName("Atualiza produto sem erros, se ele existir ")
    void deveAtualizarProduto(){

        Produto produtoSalvo = new Produto(
                1L,
                "Ventilador",
                new QuantidadeEstoque(BigDecimal.valueOf(10)),
                new QuantidadeEstoque(BigDecimal.valueOf(20)),
                UnidadeMedidaProduto.UN,
                CategoriaProduto.OUTROS,
                null,
                Situacao.ATIVO
        );

        when(produtoRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(produtoSalvo));
        when(produtoRepositoryPort.salvar(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Produto produtoAtualizado = atualizarProdutoService.executar(
                1L,
                "Vassoura",
                BigDecimal.valueOf(10),
                UnidadeMedidaProduto.UN,
                CategoriaProduto.LIMPEZA,
                LocalDate.of(2000, 2, 2),
                Situacao.ATIVO
        );

        assertEquals("Vassoura", produtoAtualizado.getNome());
        assertEquals(new QuantidadeEstoque(BigDecimal.valueOf(10)), produtoAtualizado.getEstoqueMinimo());
        assertEquals(UnidadeMedidaProduto.UN, produtoAtualizado.getUnidadeMedidaProduto());
        assertEquals(LocalDate.of(2000,2,2), produtoAtualizado.getDataValidade());
        assertEquals(Situacao.ATIVO, produtoAtualizado.getSituacao());

        verify(produtoRepositoryPort).buscarPorId(1L);
        verify(produtoRepositoryPort).salvar(any(Produto.class));

    }

    @Test
    @DisplayName("Lança EntidadeNaoEncontradaException se id não existir")
    void deveLancarExcecaoSeProdutoNaoExiste(){

        when(produtoRepositoryPort.buscarPorId(1L)).thenReturn(Optional.empty());

        EntidadeNaoEncontradoException entidadeNaoEncontradoException = assertThrows(
                EntidadeNaoEncontradoException.class,
                ()-> atualizarProdutoService.executar(1L, "cabo", null, null, null, null, null));

        assertEquals("Produto não encontrado", entidadeNaoEncontradoException.getMessage());
        verify(produtoRepositoryPort).buscarPorId(1L);
        verify(produtoRepositoryPort, never()).salvar(any(Produto.class));
    }

}
