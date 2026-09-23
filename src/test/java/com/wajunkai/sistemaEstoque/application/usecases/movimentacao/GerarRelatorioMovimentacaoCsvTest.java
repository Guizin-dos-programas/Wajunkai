package com.wajunkai.sistemaEstoque.application.usecases.movimentacao;

import com.wajunkai.sistemaEstoque.application.ports.outbound.GerarRelatorioCsvPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.MovimentacaoRepositoryPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoRelatorioCsv;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GerarRelatorioMovimentacaoCsvTest {

    @Mock
    private MovimentacaoRepositoryPort movimentacaoRepositoryPort;

    @Mock
    private GerarRelatorioCsvPort gerarRelatorioCsvPort;

    @Mock
    private UsuarioRepositoryPort usuarioRepositoryPort;

    @InjectMocks
    private GerarRelatorioMovimentacaoCsvService gerarRelatorioMovimentacaoCsvService;

    @Test
    @DisplayName("Deve gerar o relatório CSV com sucesso quando houver movimentações")
    void deveGerarRelatorioCsvComSucesso() {

        TipoRelatorioCsv tipo = TipoRelatorioCsv.DOACOES;
        LocalDateTime inicio = LocalDateTime.now().minusDays(7);
        LocalDateTime fim = LocalDateTime.now();

        Movimentacao movimentacaoMock = mock(Movimentacao.class);
        when(movimentacaoMock.getUsuarioId()).thenReturn(1L);
        List<Movimentacao> movimentacoes = List.of(movimentacaoMock);

        Map<Long, String> nomesUsuarios = Map.of(1L, "João da Silva");
        byte[] bytesEsperados = "col1,col2\nval1,val2".getBytes();

        when(movimentacaoRepositoryPort.buscarPorPeriodoETipo(inicio, fim, tipo))
                .thenReturn(movimentacoes);
        when(usuarioRepositoryPort.buscarNomesPorIds(Set.of(1L)))
                .thenReturn(nomesUsuarios);
        when(gerarRelatorioCsvPort.executar(tipo, movimentacoes, nomesUsuarios))
                .thenReturn(bytesEsperados);

        byte[] resultado = gerarRelatorioMovimentacaoCsvService.executar(tipo, inicio, fim);

        assertNotNull(resultado);
        assertArrayEquals(bytesEsperados, resultado);
        verify(movimentacaoRepositoryPort, times(1)).buscarPorPeriodoETipo(inicio, fim, tipo);
        verify(usuarioRepositoryPort, times(1)).buscarNomesPorIds(Set.of(1L));
        verify(gerarRelatorioCsvPort, times(1)).executar(tipo, movimentacoes, nomesUsuarios);
    }

    @Test
    @DisplayName("Deve gerar o relatório CSV vazio quando não houver movimentações no período")
    void deveGerarRelatorioCsvVazioQuandoNaoHouverMovimentacoes() {

        TipoRelatorioCsv tipo = TipoRelatorioCsv.SAIDA_RESIDENTE;
        LocalDateTime inicio = LocalDateTime.now().minusDays(7);
        LocalDateTime fim = LocalDateTime.now();

        List<Movimentacao> movimentacoesVogais = List.of();
        byte[] bytesVazios = "cabecalho_vazio".getBytes();

        when(movimentacaoRepositoryPort.buscarPorPeriodoETipo(inicio, fim, tipo))
                .thenReturn(movimentacoesVogais);

        when(usuarioRepositoryPort.buscarNomesPorIds(Set.of()))
                .thenReturn(Map.of());

        when(gerarRelatorioCsvPort.executar(eq(tipo), eq(movimentacoesVogais), any(Map.class)))
                .thenReturn(bytesVazios);

        byte[] resultado = gerarRelatorioMovimentacaoCsvService.executar(tipo, inicio, fim);

        assertNotNull(resultado);
        assertArrayEquals(bytesVazios, resultado);

        verify(movimentacaoRepositoryPort, times(1)).buscarPorPeriodoETipo(inicio, fim, tipo);
        verify(usuarioRepositoryPort, times(1)).buscarNomesPorIds(Set.of());
        verify(gerarRelatorioCsvPort, times(1)).executar(eq(tipo), eq(movimentacoesVogais), any(Map.class));
    }
}