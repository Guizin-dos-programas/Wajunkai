package com.wajunkai.sistemaEstoque.application.ports.outbound;

import com.wajunkai.sistemaEstoque.domain.enums.movimentacao.TipoRelatorioCsv;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;

import java.util.List;
import java.util.Map;

public interface GerarRelatorioCsvPort {

    byte[] executar(
            TipoRelatorioCsv tipoRelatorioCsv,
            List<Movimentacao> movimentacoes,
            Map<Long, String> nomesUsuarios
    );
}