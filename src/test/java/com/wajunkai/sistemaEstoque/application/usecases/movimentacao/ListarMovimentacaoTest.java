package com.wajunkai.sistemaEstoque.application.usecases.movimentacao;

import com.wajunkai.sistemaEstoque.application.dtos.movimentacao.PaginaQueryMovimentacao;
import com.wajunkai.sistemaEstoque.application.dtos.movimentacao.PaginaResultadoMovimentacao;
import com.wajunkai.sistemaEstoque.application.ports.outbound.MovimentacaoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.MovimentacaoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarMovimentacaoTest {

    @Mock
    private MovimentacaoRepositoryPort movimentacaoRepositoryPort;

    @InjectMocks
    private ListarMovimentacaoService listarMovimentacaoService;

    private Movimentacao criarMovimentacaoMockada() {
        Produto produtoMock = mock(Produto.class);
        when(produtoMock.getId()).thenReturn(1L);
        when(produtoMock.getNome()).thenReturn("Produto Teste");

        QuantidadeEstoque quantidadeMock = mock(QuantidadeEstoque.class);
        when(quantidadeMock.valor()).thenReturn(BigDecimal.valueOf(10));

        Movimentacao movimentacaoMock = mock(Movimentacao.class);
        when(movimentacaoMock.getId()).thenReturn(1L);
        when(movimentacaoMock.getProduto()).thenReturn(produtoMock);
        when(movimentacaoMock.getQuantidade()).thenReturn(quantidadeMock);
        when(movimentacaoMock.getDataHora()).thenReturn(LocalDateTime.now());

        return movimentacaoMock;
    }

    @Test
    @DisplayName("Deve listar movimentações filtrando por produtoId quando informado")
    void deveListarMovimentacoesPorProdutoId() {
        Long produtoId = 1L;
        PaginaQueryMovimentacao query = new PaginaQueryMovimentacao(0, 10);

        Movimentacao movimentacaoMock = criarMovimentacaoMockada();

        PaginaResultadoMovimentacao<Movimentacao> paginaDominio = new PaginaResultadoMovimentacao<>(
                List.of(movimentacaoMock),
                0,
                10,
                1L,
                1
        );

        when(movimentacaoRepositoryPort.buscarPorProduto(eq(produtoId), eq(query)))
                .thenReturn(paginaDominio);

        PaginaResultadoMovimentacao<MovimentacaoResponse> resultado = listarMovimentacaoService.executar(produtoId, query);

        assertNotNull(resultado);
        assertEquals(1, resultado.conteudo().size());
        assertEquals(0, resultado.paginaAtual());
        assertEquals(10, resultado.tamanhoPagina());
        assertEquals(1L, resultado.totalElementos());
        assertEquals(1, resultado.totalPaginas());

        verify(movimentacaoRepositoryPort, times(1)).buscarPorProduto(produtoId, query);
        verify(movimentacaoRepositoryPort, never()).buscarTodas(any());
    }

    @Test
    @DisplayName("Deve listar todas as movimentações quando produtoId for nulo")
    void deveListarTodasMovimentacoesQuandoProdutoIdForNulo() {
        Long produtoId = null;
        PaginaQueryMovimentacao query = new PaginaQueryMovimentacao(0, 10);

        Movimentacao movimentacaoMock = criarMovimentacaoMockada();

        PaginaResultadoMovimentacao<Movimentacao> paginaDominio = new PaginaResultadoMovimentacao<>(
                List.of(movimentacaoMock),
                0,
                10,
                1L,
                1
        );

        when(movimentacaoRepositoryPort.buscarTodas(eq(query)))
                .thenReturn(paginaDominio);

        PaginaResultadoMovimentacao<MovimentacaoResponse> resultado = listarMovimentacaoService.executar(produtoId, query);

        assertNotNull(resultado);
        assertEquals(1, resultado.conteudo().size());

        verify(movimentacaoRepositoryPort, times(1)).buscarTodas(query);
        verify(movimentacaoRepositoryPort, never()).buscarPorProduto(any(), any());
    }
}