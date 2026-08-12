package com.wajunkai.sistemaEstoque.application.usecases.produto;

import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.produto.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.enums.produto.UnidadeMedidaProduto;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeJaCadastradaException;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CadastrarProdutoTest {

    @Mock
    private ProdutoRepositoryPort produtoRepositoryPort;

    @InjectMocks
    private CadastrarProdutoService cadastrarProdutoService;

    @Test
    @DisplayName("Deve cadastrar produto e campos corretos")
    void deveCadastrarProdutoSemErro(){
        Produto produto = new Produto(
                1L,
                "Oleo de cozinha",
                new QuantidadeEstoque(BigDecimal.valueOf(10)),
                new QuantidadeEstoque(BigDecimal.valueOf(10)),
                UnidadeMedidaProduto.L,
                CategoriaProduto.ALIMENTACAO,
                null,
                Situacao.ATIVO
        );

        when(produtoRepositoryPort.salvar(any(Produto.class))).thenReturn(produto);

        Produto resultado = cadastrarProdutoService.executar(
                produto.getNome(),
                produto.getQuantidadeAtual().valor(),
                produto.getEstoqueMinimo().valor(),
                produto.getUnidadeMedidaProduto(),
                produto.getCategoriaProduto(),produto.getDataValidade(),
                produto.getSituacao());

        assertNotNull(resultado);
        assertEquals("Oleo de cozinha", resultado.getNome());
        assertEquals(BigDecimal.valueOf(10), resultado.getQuantidadeAtual().valor());
        assertEquals(BigDecimal.valueOf(10), resultado.getEstoqueMinimo().valor());
        assertEquals(UnidadeMedidaProduto.L, resultado.getUnidadeMedidaProduto());
        assertEquals(CategoriaProduto.ALIMENTACAO, resultado.getCategoriaProduto());
        assertNull(resultado.getDataValidade());
        assertEquals(Situacao.ATIVO, resultado.getSituacao());

        verify(produtoRepositoryPort).salvar(argThat(p ->
                        p.getNome().equals("Oleo de cozinha") &&
                        p.getQuantidadeAtual().valor().equals(BigDecimal.valueOf(10)) &&
                        p.getEstoqueMinimo().valor().equals(BigDecimal.valueOf(10)) &&
                        p.getUnidadeMedidaProduto() == UnidadeMedidaProduto.L &&
                        p.getCategoriaProduto() == CategoriaProduto.ALIMENTACAO &&
                        p.getDataValidade() == null &&
                        p.getSituacao() == Situacao.ATIVO
        ));
    }

    @Test
    @DisplayName("Deve lançar exceção se produto já estiver cadastrado")
    void deveLancarExcecaoSeProdutoJaCadastrado() {

        when(produtoRepositoryPort.existePorNome("Oleo de cozinha"))
                .thenReturn(true);

        assertThrows(
                EntidadeJaCadastradaException.class,
                () -> cadastrarProdutoService.executar(
                        "Oleo de cozinha",
                        BigDecimal.valueOf(10),
                        BigDecimal.valueOf(10),
                        UnidadeMedidaProduto.L,
                        CategoriaProduto.ALIMENTACAO,
                        null,
                        Situacao.ATIVO
                )
        );

        verify(produtoRepositoryPort)
                .existePorNome("Oleo de cozinha");

        verify(produtoRepositoryPort, never())
                .salvar(any(Produto.class));
    }
}
