package com.wajunkai.sistemaEstoque.application.ports.inbound.usuario;

import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.domain.valueObject.Login;
import com.wajunkai.sistemaEstoque.infrastructure.web.dto.request.AtualizarMeusDadosRequest;

public interface AtualizarMinhasCredenciaisUsecase {
    Usuario executar(Login login, AtualizarMeusDadosRequest atualizarCredenciaisRequest);
}
