CREATE TYPE unidade_medida_enum AS ENUM ('UN', 'KG', 'L', 'CX', 'PCT');
CREATE TYPE categoria_enum AS ENUM ('COZINHA', 'ENFERMAGEM', 'LIMPEZA', 'OUTROS');

CREATE TABLE tb_produto (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, -- Ou GENERATED ALWAYS AS IDENTITY
    nome VARCHAR(150) NOT NULL,
    quantidade_atual NUMERIC(10, 3) NOT NULL DEFAULT 0.000,
    estoque_minimo NUMERIC(10, 3) NOT NULL DEFAULT 0.000,
    unidade_medida unidade_medida_enum NOT NULL,
    categoria categoria_enum NOT NULL
);