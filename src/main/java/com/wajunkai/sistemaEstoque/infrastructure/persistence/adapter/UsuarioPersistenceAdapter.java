package com.wajunkai.sistemaEstoque.infrastructure.persistence.adapter;

import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.domain.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.UsuarioJpaEntity;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.mapper.UsuarioMapper;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.repository.SpringDataUsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioPersistenceAdapter implements UsuarioRepositoryPort {

    private final UsuarioMapper mapper;
    private final SpringDataUsuarioRepository repository;

    public UsuarioPersistenceAdapter(UsuarioMapper mapper, SpringDataUsuarioRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }


    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioJpaEntity entity = mapper.toEntity(usuario);
        UsuarioJpaEntity entitySalva = repository.save(entity);

        return mapper.toDomain(entitySalva);
    }

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorLogin(String login) {
        return repository.findByLogin(login).map(mapper::toDomain);
    }

    @Override
    public boolean existePorLogin(String loginString) {
        return repository.existsByLogin(loginString);
    }
}
