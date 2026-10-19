ALTER TABLE tb_produto
    DROP COLUMN data_validade;

ALTER TABLE movimentacao
    ADD COLUMN data_validade DATE;