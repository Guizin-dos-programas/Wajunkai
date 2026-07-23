package com.wajunkai.sistemaEstoque.infrastructure.persistence.adapter;

import com.wajunkai.sistemaEstoque.application.ports.outbound.ProdutoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.model.Produto;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.ProdutoJpaEntity;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.mapper.ProdutoMapper;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.repository.SpringDataProdutoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProdutoPersistenceAdapter implements ProdutoRepositoryPort {

    private final SpringDataProdutoRepository springDataProdutoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoPersistenceAdapter(SpringDataProdutoRepository springDataProdutoRepository, ProdutoMapper produtoMapper) {
        this.springDataProdutoRepository = springDataProdutoRepository;
        this.produtoMapper = produtoMapper;
    }


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
}
