package com.wajunkai.sistemaEstoque.application.usecases;

import com.wajunkai.sistemaEstoque.domain.enums.TipoUsuario;
import com.wajunkai.sistemaEstoque.domain.exceptions.UsuarioJaCadastradoException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.domain.ports.inbound.CadastrarUsuarioUseCase;
import com.wajunkai.sistemaEstoque.domain.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.domain.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.valueObject.Login;


public class CadastrarUsuarioService  implements CadastrarUsuarioUseCase {

        private final UsuarioRepositoryPort usuarioRepository;
        private final PasswordEncoderPort passwordEncoder;

        public CadastrarUsuarioService(UsuarioRepositoryPort usuarioRepository, PasswordEncoderPort passwordEncoder) {
            this.usuarioRepository = usuarioRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        public Usuario executar(String nome, String loginBruto, String senhaLimpa, TipoUsuario tipoUsuario) {

            Login loginVO = new Login(loginBruto, null);

            if (usuarioRepository.existePorLogin(loginVO.valor())) {
                throw new UsuarioJaCadastradoException(
                        "Identificador informado já está em uso"
                );
            }

            String senhaCriptografada = passwordEncoder.encode(senhaLimpa);

            Usuario novoUsuario = new Usuario(nome, loginVO, senhaCriptografada, tipoUsuario);

            return usuarioRepository.salvar(novoUsuario);
        }


}
