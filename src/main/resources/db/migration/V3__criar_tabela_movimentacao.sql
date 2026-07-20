-- 1. Garanta que o TYPE seja criado primeiro
CREATE TYPE tipo_movimentacao_enum AS ENUM ('ENTRADA_DOACAO', 'ENTRADA_COMPRA', 'SAIDA_CONSUMO', 'SAIDA_PERDA');

-- 2. Criação da tabela de movimentação
CREATE TABLE movimentacao (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    -- Chaves Estrangeiras (Auditoria e Relacionamento)
    produto_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL, -- Carimbo obrigatório para auditoria

    -- Dados da Movimentação
    tipo_movimentacao tipo_movimentacao_enum NOT NULL,
    quantidade NUMERIC(10,3) NOT NULL, -- Perfeito para KG (15.750) ou Unidades (10.000)
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Campos da folha física antiga
    doador_nome VARCHAR(100) DEFAULT 'Institucional / Não informado',
    cidade VARCHAR(50) DEFAULT 'Maringá', -- Select padronizado no frontend

    -- Regras de Negócio Específicas
    valor_compra NUMERIC(10, 2) DEFAULT NULL, -- Usado na RN02 (Entrada Compra)
    residente_nome VARCHAR(100) DEFAULT NULL, -- Usado na RN04 (Saída Nominal Enfermagem)

    -- Restrições (Certifique-se que os nomes das tabelas 'produtos' e 'usuarios' estão corretos)
    CONSTRAINT fk_movimentacao_produto FOREIGN KEY (produto_id) REFERENCES tb_produto(id) ON DELETE RESTRICT,
    CONSTRAINT fk_movimentacao_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuario(id) ON DELETE RESTRICT
);

-- 3. BOAS PRÁTICAS: Índices para acelerar os filtros de relatório do Igor
CREATE INDEX idx_movimentacao_produto ON movimentacao(produto_id);
CREATE INDEX idx_movimentacao_usuario ON movimentacao(usuario_id);
CREATE INDEX idx_movimentacao_cidade ON movimentacao(cidade);