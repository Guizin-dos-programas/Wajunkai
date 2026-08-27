package com.wajunkai.sistemaEstoque.infrastructure.persistence.adapter;

import com.wajunkai.sistemaEstoque.application.ports.outbound.GerarRelatorioCsvPort;
import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoRelatorioCsv;
import com.wajunkai.sistemaEstoque.domain.model.ColunaCsv;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("csvExporter")
public class RelatorioCsvAdapter implements GerarRelatorioCsvPort {

    private static final String SEPARADOR = ";";

    @Override
    public byte[] executar(
            TipoRelatorioCsv tipoRelatorioCsv,
            List<Movimentacao> movimentacoes,
            Map<Long, String> nomesUsuarios) {

        StringBuilder csv = new StringBuilder();
        csv.append('\uFEFF');

        List<ColunaCsv> colunas = tipoRelatorioCsv.getColunas();

        String cabecalho = colunas.stream()
                .map(ColunaCsv::getHeader)
                .collect(Collectors.joining(SEPARADOR));

        csv.append(cabecalho).append("\n");

        for (Movimentacao m : movimentacoes) {
            String linha = colunas.stream()
                    .map(coluna -> {

                        if ("Responsável".equals(coluna.getHeader())) {
                            String nomeUsuario = nomesUsuarios.get(m.getUsuarioId());
                            return sanitizar(nomeUsuario);
                        }

                        return sanitizar(coluna.extrair(m));
                    })
                    .collect(Collectors.joining(SEPARADOR));

            csv.append(linha).append("\n");
        }

        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }

    private String sanitizar(String valor) {
        if (valor == null) return "";

        String limpo = valor.replace("\n", " ").replace("\r", " ");

        if (limpo.contains(SEPARADOR) || limpo.contains("\"")) {
            limpo = "\"" + limpo.replace("\"", "\"\"") + "\"";
        }

        return limpo;
    }
}