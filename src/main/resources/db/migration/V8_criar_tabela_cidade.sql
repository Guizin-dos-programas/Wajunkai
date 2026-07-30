CREATE TABLE tb_cidade (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL DEFAULT 'PR',
    codigo_ibge INT
);

INSERT INTO tb_cidade (nome, uf) VALUES
('Maringá', 'PR'),
('Sarandi', 'PR'),
('Paiçandu', 'PR'),
('Mandaguari', 'PR'),
('Marialva', 'PR'),
('Cianorte', 'PR'),
('Umuarama', 'PR');

ALTER TABLE movimentacao
ADD COLUMN cidade_id INT DEFAULT 1
CONSTRAINT fk_movimentacao_cidade FOREIGN KEY (cidade_id) REFERENCES tb_cidade(id) ON DELETE RESTRICT;

CREATE INDEX idx_movimentacao_cidade ON movimentacao(cidade_id);