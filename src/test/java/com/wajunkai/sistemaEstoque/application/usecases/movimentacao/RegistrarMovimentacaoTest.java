package com.wajunkai.sistemaEstoque.application.usecases.movimentacao;

import com.wajunkai.sistemaEstoque.application.ports.outbound.MovimentacaoRepositoryPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoMovimentacao;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeNaoEncontradoException;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.domain.valueObject.QuantidadeEstoque;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.movimentacao.RegistrarMovimentacaoRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrarMovimentacaoTest {

    @Mock
    private MovimentacaoRepositoryPort movimentacaoRepositoryPort;

    @Mock
    private ProdutoRepositoryPort produtoRepositoryPort;

    @InjectMocks
    private RegistrarMovimentacaoService registrarMovimentacaoService;

    @Test
    @DisplayName("Deve registrar uma movimentação de estoque com sucesso")
    void deveRegistrarMovimentacaoComSucesso() {

        Long produtoId = 1L;
        RegistrarMovimentacaoRequest request = new RegistrarMovimentacaoRequest(
                produtoId,
                10L,
                TipoMovimentacao.ENTRADA_DOACAO,
                BigDecimal.valueOf(1),
                "Doador Exemplo",
                "Maringá",
                BigDecimal.valueOf(100.00),
                "Residente Exemplo"
        );

        Produto produtoMock = mock(Produto.class);

        when(produtoRepositoryPort.buscarPorId(produtoId)).thenReturn(Optional.of(produtoMock));

        Movimentacao movimentacaoSalvaMock = mock(Movimentacao.class);
        when(produtoMock.isAtivo()).thenReturn(true);
        when(movimentacaoRepositoryPort.salvar(any(Movimentacao.class))).thenReturn(movimentacaoSalvaMock);

        Movimentacao resultado = registrarMovimentacaoService.executar(request);

        assertNotNull(resultado);

        verify(produtoRepositoryPort, times(1)).buscarPorId(produtoId);
        verify(produtoMock, times(1)).aplicarMovimentacao(eq(request.tipoMovimentacao()), any(QuantidadeEstoque.class));
        verify(produtoRepositoryPort, times(1)).salvar(produtoMock);

        verify(movimentacaoRepositoryPort, times(1)).salvar(any(Movimentacao.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o produto não for encontrado ao registrar movimentação")
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {

        Long produtoIdInexistente = 99L;
        RegistrarMovimentacaoRequest request = new RegistrarMovimentacaoRequest(
                produtoIdInexistente,
                10L,
                TipoMovimentacao.ENTRADA_DOACAO,
                BigDecimal.valueOf(1),
                "Doador",
                "Maringá",
                BigDecimal.ZERO,
                "Residente"
        );

        when(produtoRepositoryPort.buscarPorId(produtoIdInexistente)).thenReturn(Optional.empty());

        EntidadeNaoEncontradoException excecao = assertThrows(
                EntidadeNaoEncontradoException.class,
                () -> registrarMovimentacaoService.executar(request)
        );

        assertEquals("Produto não encontrado", excecao.getMessage());


        verify(produtoRepositoryPort, times(1)).buscarPorId(produtoIdInexistente);
        verify(produtoRepositoryPort, never()).salvar(any());
        verify(movimentacaoRepositoryPort, never()).salvar(any());
    }
}
