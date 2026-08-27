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
import java.util.function.Function;
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

        Map<Long, String> nomesUsuarios = movimentacaoList.stream()
                .map(Movimentacao::getUsuarioId)
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),
                        id -> usuarioRepositoryPort.buscarPorId(id)
                                .map(usuario -> usuario.getNome())
                                .orElse("Usuário não encontrado")
                ));

        return gerarRelatorioCsvPort.executar(
                tipo,
                movimentacaoList,
                nomesUsuarios
        );
    }
}