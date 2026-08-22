package com.wajunkai.sistemaEstoque.application.usecases.produto;

import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaQuery;
import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaResultado;
import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.produto.CategoriaProduto;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.enums.produto.UnidadeMedidaProduto;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarProdutosPaginacaoTest {

    @Mock
    private ProdutoRepositoryPort produtoRepositoryPort;

    @InjectMocks
    private ListarProdutosService listarProdutosService;

    @Test
    @DisplayName("Retorna produtos cadastrados com paginação")
    void listaProdutos(){
        Produto produto1 = new Produto(
                1L,
                "Carne",
                QuantidadeEstoque.obrigatorio(BigDecimal.valueOf(100)),
                QuantidadeEstoque.obrigatorio(BigDecimal.valueOf(20)),
                UnidadeMedidaProduto.KG,
                CategoriaProduto.ALIMENTACAO,
                LocalDate.of(2025, 6, 29),
                Situacao.ATIVO
        );

        Produto produto2 = new Produto(
                2L,
                "Frango",
                QuantidadeEstoque.obrigatorio(BigDecimal.valueOf(100)),
                QuantidadeEstoque.obrigatorio(BigDecimal.valueOf(20)),
                UnidadeMedidaProduto.KG,
                CategoriaProduto.ALIMENTACAO,
                LocalDate.of(2025, 5, 29),
                Situacao.ATIVO
        );

        PaginaResultado<Produto> paginaResultado = new PaginaResultado<>(List.of(produto1, produto2),
                0,
                5,
                10,
                1
                );

        when(produtoRepositoryPort.listarTodos(new PaginaQuery(0, 5, Situacao.ATIVO), Situacao.ATIVO )).thenReturn(paginaResultado);

        PaginaResultado<Produto> resultado = listarProdutosService.executar(new PaginaQuery(0, 5, Situacao.ATIVO), Situacao.ATIVO);

        assertNotNull(resultado);
        assertEquals(produto1, resultado.conteudo().getFirst());
        assertEquals(produto2, resultado.conteudo().get(1));
        assertEquals(2, resultado.conteudo().size());
        assertEquals(5, resultado.tamanhoPagina());
        assertEquals(10, resultado.totalElementos());
        assertEquals(1, resultado.totalPaginas());

        verify(produtoRepositoryPort).listarTodos(new PaginaQuery(0, 5, Situacao.ATIVO), Situacao.ATIVO);
    }
}
