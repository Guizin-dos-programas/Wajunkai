CREATE TABLE tb_refresh_token(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    token varchar(255) NOT NULL UNIQUE,
    usuario_id BIGINT NOT NULL,
    data_expiracao TIMESTAMP NOT NULL,
    revogado BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_refresh_token_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuario(id) ON DELETE CASCADE
);
CREATE INDEX idx_refresh_token_token ON tb_refresh_token(token);
