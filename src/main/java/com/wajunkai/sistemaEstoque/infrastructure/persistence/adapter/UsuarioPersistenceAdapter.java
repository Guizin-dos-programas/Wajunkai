package com.wajunkai.sistemaEstoque.infrastructure.persistence.adapter;

import com.wajunkai.sistemaEstoque.application.dtos.usuario.PaginaResultado;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.UsuarioJpaEntity;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.mapper.UsuarioMapper;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.repository.SpringDataUsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioPersistenceAdapter implements UsuarioRepositoryPort {

    private final UsuarioMapper mapper;
    private final SpringDataUsuarioRepository repository;

    public UsuarioPersistenceAdapter(UsuarioMapper mapper, SpringDataUsuarioRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Transactional
    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioJpaEntity entity = mapper.toEntity(usuario);
        UsuarioJpaEntity entitySalva = repository.save(entity);

        return mapper.toDomain(entitySalva);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
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

    @Override
    public PaginaResultado<Usuario> buscarTodosPaginado(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by(Sort.Direction.ASC, "id"));

        Page<UsuarioJpaEntity> pageEntities = repository.findAll(pageable);

        List<Usuario> usuarios = pageEntities.getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();

        return new PaginaResultado<>(
                usuarios,
                pageEntities.getNumber(),
                pageEntities.getSize(),
                pageEntities.getTotalElements(),
                pageEntities.getTotalPages()
        );
    }
}
