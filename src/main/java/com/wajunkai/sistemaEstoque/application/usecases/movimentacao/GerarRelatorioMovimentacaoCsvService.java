package com.wajunkai.sistemaEstoque.application.usecases.movimentacao;

import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.ExportarCsvUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.GerarRelatorioCsvPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.MovimentacaoRepositoryPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoRelatorioCsv;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GerarRelatorioMovimentacaoCsvService implements ExportarCsvUsecase {

    private final MovimentacaoRepositoryPort movimentacaoRepositoryPort;
    private final GerarRelatorioCsvPort gerarRelatorioCsvPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public GerarRelatorioMovimentacaoCsvService(
            MovimentacaoRepositoryPort movimentacaoRepositoryPort,
            @Qualifier("csvExporter") GerarRelatorioCsvPort gerarRelatorioCsvPort,
            UsuarioRepositoryPort usuarioRepositoryPort) {

        this.movimentacaoRepositoryPort = movimentacaoRepositoryPort;
        this.gerarRelatorioCsvPort = gerarRelatorioCsvPort;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public byte[] executar(
            TipoRelatorioCsv tipo,
            LocalDateTime inicio,
            LocalDateTime fim) {

        List<Movimentacao> movimentacaoList =
                movimentacaoRepositoryPort.buscarPorPeriodoETipo(inicio, fim, tipo);

        Set<Long> usuarioIds = movimentacaoList.stream()
                .map(Movimentacao::getUsuarioId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, String> nomesUsuarios = usuarioRepositoryPort.buscarNomesPorIds(usuarioIds);

        if (movimentacaoList.isEmpty()) {
            return gerarRelatorioCsvPort.executar(tipo, movimentacaoList, Map.of());
        }

        return gerarRelatorioCsvPort.executar(
                tipo,
                movimentacaoList,
                nomesUsuarios
        );
    }
}