CREATE TYPE role_perfil_enum AS ENUM ('ADM', 'COZINHA', 'ENFERMAGEM', 'LIMPEZA', 'ASSISTENCIA SOCIAL');

ALTER TABLE tb_usuario
  ALTER COLUMN role_perfil TYPE role_perfil_enum
  USING role_perfil::role_perfil_enum;