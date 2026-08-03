package com.wajunkai.sistemaEstoque.application.usecases.movimentacao;

import com.wajunkai.sistemaEstoque.application.ports.inbound.movimentacao.ObterBalancoDeCompraUsecase;
import com.wajunkai.sistemaEstoque.domain.exceptions.RegraDeNegocioException;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.repository.SpringDataMovimentacaoRepository;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.BalancoComprasResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ObterBalancoComprasService implements ObterBalancoDeCompraUsecase {

    private final SpringDataMovimentacaoRepository springDataMovimentacaoRepository;

    public ObterBalancoComprasService(SpringDataMovimentacaoRepository springDataMovimentacaoRepository) {
        this.springDataMovimentacaoRepository = springDataMovimentacaoRepository;
    }

    @Transactional
    @Override
    public BalancoComprasResponse executar(LocalDate inicio, LocalDate fim) {

            if (inicio == null || fim == null) {
                throw new RegraDeNegocioException("As datas de início e fim são obrigatórias para gerar o balanço.");
            }

            if (inicio.isAfter(fim)) {
                throw new RegraDeNegocioException("A data de início não pode ser posterior à data de fim.");
            }

            LocalDateTime dataInicio = inicio.atStartOfDay();
            LocalDateTime dataFim = fim.atTime(LocalTime.MAX);

            BigDecimal valorTotal = springDataMovimentacaoRepository.somarTotalGastoComCompras(dataInicio, dataFim);
            Long quantidadeCompras = springDataMovimentacaoRepository.contarTotalComprasNoPeriodo(dataInicio, dataFim);

            List<Object[]> resultadosCategoria = springDataMovimentacaoRepository.agruparGastosPorCategoria(dataInicio, dataFim);

            List<BalancoComprasResponse.ItemBalancoCategoria> gastosPorCategoria = resultadosCategoria.stream()
                    .map(obj -> new BalancoComprasResponse.ItemBalancoCategoria(
                            obj[0] != null ? obj[0].toString() : "SEM CATEGORIA",
                            (BigDecimal) obj[1]
                    ))
                    .toList();

            return new BalancoComprasResponse(
                    inicio,
                    fim,
                    valorTotal,
                    quantidadeCompras,
                    gastosPorCategoria
            );
   }
}
