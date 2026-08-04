package com.wajunkai.sistemaEstoque.application.usecases.movimentacao;

import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.ExportarBalancoPdfUsecase;
import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.ObterBalancoDeCompraUsecase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.GerarRelatorioPdfPort;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.BalancoComprasResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ExportarBalancoPdfService implements ExportarBalancoPdfUsecase {

    private final GerarRelatorioPdfPort gerarRelatorioPdfPort;
    private final ObterBalancoDeCompraUsecase obterBalancoDeCompraUsecase;

    public ExportarBalancoPdfService(GerarRelatorioPdfPort gerarRelatorioPdfPort, ObterBalancoDeCompraUsecase obterBalancoDeCompraUsecase) {
        this.gerarRelatorioPdfPort = gerarRelatorioPdfPort;
        this.obterBalancoDeCompraUsecase = obterBalancoDeCompraUsecase;
    }

    @Override
    public byte[] executar(LocalDate dataInicio, LocalDate dataFim) {
        BalancoComprasResponse balanco = obterBalancoDeCompraUsecase.executar(dataInicio, dataFim);
        return gerarRelatorioPdfPort.gerarBalancoPdf(balanco);
    }
}
