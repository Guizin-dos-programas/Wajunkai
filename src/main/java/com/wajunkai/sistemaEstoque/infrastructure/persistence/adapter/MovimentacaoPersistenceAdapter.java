package com.wajunkai.sistemaEstoque.infrastructure.persistence.adapter;

import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaQuery;
import com.wajunkai.sistemaEstoque.application.dtos.produto.PaginaResultado;
import com.wajunkai.sistemaEstoque.application.ports.outbound.MovimentacaoRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.model.Movimentacao;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.entity.MovimentacaoJpaEntity;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.mapper.MovimentacaoMapper;
import com.wajunkai.sistemaEstoque.infrastructure.persistence.repository.SpringDataMovimentacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovimentacaoPersistenceAdapter implements MovimentacaoRepositoryPort {

    private final SpringDataMovimentacaoRepository springDataMovimentacaoRepository;
    private final MovimentacaoMapper movimentacaoMapper;

    public MovimentacaoPersistenceAdapter(SpringDataMovimentacaoRepository springDataMovimentacaoRepository, MovimentacaoMapper movimentacaoMapper) {
        this.springDataMovimentacaoRepository = springDataMovimentacaoRepository;
        this.movimentacaoMapper = movimentacaoMapper;
    }

    @Override
    public Movimentacao salvar(Movimentacao movimentacao) {
        MovimentacaoJpaEntity entity = movimentacaoMapper.toEntity(movimentacao);
        MovimentacaoJpaEntity entitySalva = springDataMovimentacaoRepository.save(entity);

        return MovimentacaoMapper.toDomain(entitySalva);
    }

    @Override
    public PaginaResultado<Movimentacao> buscarPorProduto(Long produtoId, PaginaQuery query) {
        PageRequest pageable = PageRequest.of(query.pagina(), query.tamanho());
        Page<MovimentacaoJpaEntity> page = springDataMovimentacaoRepository.findByProdutoId(produtoId, pageable);

        List<Movimentacao> itens = page.getContent().stream()
                .map(MovimentacaoMapper::toDomain)
                .toList();

        return new PaginaResultado<>(
                itens,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
