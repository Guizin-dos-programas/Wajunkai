package com.wajunkai.sistemaEstoque.infrastructure.persistence.adapter;

import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaQuery;
import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaResultado;
import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.enums.produto.Situacao;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.ProdutoJpaEntity;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.mapper.ProdutoMapper;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.repository.SpringDataProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProdutoPersistenceAdapter implements ProdutoRepositoryPort {

    private final SpringDataProdutoRepository springDataProdutoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoPersistenceAdapter(SpringDataProdutoRepository springDataProdutoRepository, ProdutoMapper produtoMapper) {
        this.springDataProdutoRepository = springDataProdutoRepository;
        this.produtoMapper = produtoMapper;
    }


    @Transactional
    @Override
    public Produto salvar(Produto produto) {
        ProdutoJpaEntity entity = produtoMapper.toEntity(produto);

        ProdutoJpaEntity entitySalva = springDataProdutoRepository.save(entity);

        return produtoMapper.toDomain(entitySalva);
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return springDataProdutoRepository.findById(id).map(produtoMapper::toDomain);
    }

    @Override
    public boolean existePorNome(String nome) {
        return springDataProdutoRepository.existsByNome(nome);
    }

    @Override
    public PaginaResultado<Produto> listarTodos(PaginaQuery paginaQuery, Situacao situacao) {
        Pageable pageable = PageRequest.of(paginaQuery.pagina(), paginaQuery.tamanho());

        Page<ProdutoJpaEntity> pageEntity = springDataProdutoRepository.findBySituacao(situacao, pageable);

        List<Produto> produtos = pageEntity.getContent()
                .stream()
                .map(produtoMapper::toDomain)
                .toList();

        return new PaginaResultado<>(
                produtos,
                pageEntity.getNumber(),
                pageEntity.getSize(),
                pageEntity.getTotalElements(),
                pageEntity.getTotalPages()
        );
    }
}
