package com.wajunkai.sistemaEstoque.application.usecases.usuario;

import com.wajunkai.sistemaEstoque.domain.enums.usuario.TipoUsuario;
import com.wajunkai.sistemaEstoque.domain.exceptions.EntidadeJaCadastradaException;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.application.ports.inbound.usuario.CadastrarUsuarioUseCase;
import com.wajunkai.sistemaEstoque.application.ports.outbound.PasswordEncoderPort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.valueObject.Login;
import org.springframework.stereotype.Service;

@Service
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
                throw new EntidadeJaCadastradaException(
                        "Identificador informado já está em uso"
                );
            }

            String senhaCriptografada = passwordEncoder.encode(senhaLimpa);

            Usuario novoUsuario = new Usuario(nome, loginVO, senhaCriptografada, tipoUsuario);

            return usuarioRepository.salvar(novoUsuario);
        }


}
