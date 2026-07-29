DROP TYPE categoria_enum CASCADE;

CREATE TYPE categoria_enum AS ENUM (
    'ALIMENTACAO',
    'ENFERMAGEM_HIGIENE',
    'FARMACIA',
    'LIMPEZA',
    'MANUTENCAO',
    'OUTROS'
);

ALTER TABLE tb_produto
ADD COLUMN data_validade DATE NULL;
