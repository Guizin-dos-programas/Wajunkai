package com.wajunkai.sistemaEstoque.application.ports.outbound;

import com.wajunkai.sistemaEstoque.infrastructure.web.dto.response.BalancoComprasResponse;

public interface GerarRelatorioPdfPort {
    byte [] gerarBalancoPdf(BalancoComprasResponse balancoComprasResponse);
}
