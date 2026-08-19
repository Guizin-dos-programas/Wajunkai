package com.wajunkai.sistemaEstoque.application.usecases.movimentacao;

import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.ExportarCsvUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.GerarRelatorioCsvPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.MovimentacaoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoRelatorioCsv;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GerarRelatorioMovimentacaoCsvService implements ExportarCsvUsecase {

    private final MovimentacaoRepositoryPort movimentacaoRepositoryPort;
    private final GerarRelatorioCsvPort gerarRelatorioCsvPort;

    public GerarRelatorioMovimentacaoCsvService(MovimentacaoRepositoryPort movimentacaoRepositoryPort, @Qualifier("csvExporter") GerarRelatorioCsvPort gerarRelatorioCsvPort) {
        this.movimentacaoRepositoryPort = movimentacaoRepositoryPort;
        this.gerarRelatorioCsvPort = gerarRelatorioCsvPort;
    }


    @Override
    public byte[] executar(TipoRelatorioCsv tipo, LocalDateTime inicio, LocalDateTime fim) {
        List<Movimentacao> movimentacaoList = movimentacaoRepositoryPort.buscarPorPeriodoETipo(inicio, fim, tipo);
        return gerarRelatorioCsvPort.executar(tipo, movimentacaoList);
    }

}
