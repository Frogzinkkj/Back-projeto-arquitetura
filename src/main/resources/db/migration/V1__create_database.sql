-- ────────────────────────────────────────────────────────────
-- 0. EXTENSÕES
-- ────────────────────────────────────────────────────────────
CREATE EXTENSION IF NOT EXISTS "pgcrypto";   
CREATE EXTENSION IF NOT EXISTS "unaccent";   

-- ────────────────────────────────────────────────────────────
-- 1. IES  
-- ────────────────────────────────────────────────────────────
CREATE TABLE ies (
                     id        BIGSERIAL     PRIMARY KEY,
                     nome      VARCHAR(200)  NOT NULL,
                     endereco  VARCHAR(300),
                     telefone  VARCHAR(20),
                     status    VARCHAR(10)   NOT NULL DEFAULT 'ATIVO'
                         CHECK (status IN ('ATIVO','INATIVO'))
);

-- ────────────────────────────────────────────────────────────
-- 2. ESCOLA
-- ────────────────────────────────────────────────────────────
CREATE TABLE escola (
                        id             BIGSERIAL     PRIMARY KEY,
                        nome           VARCHAR(200)  NOT NULL,
                        status         VARCHAR(10)   NOT NULL DEFAULT 'ATIVO'
                            CHECK (status IN ('ATIVO','INATIVO')),
                        coordenador    VARCHAR(200),
                        ies_id         BIGINT        NOT NULL
                            REFERENCES ies(id) ON DELETE RESTRICT
);

-- ────────────────────────────────────────────────────────────
-- 3. PROFESSOR
-- ────────────────────────────────────────────────────────────
CREATE TABLE professor (
                           id             BIGSERIAL     PRIMARY KEY,
                           matricula      VARCHAR(50)   NOT NULL UNIQUE,
                           nome           VARCHAR(200)  NOT NULL,
                           email          VARCHAR(200)  NOT NULL UNIQUE,
                           telefone       VARCHAR(20),
                           escola_id      BIGINT        NOT NULL
                               REFERENCES escola(id) ON DELETE RESTRICT,
                           data_cadastro  DATE          NOT NULL DEFAULT CURRENT_DATE,
                           status         VARCHAR(10)   NOT NULL DEFAULT 'ATIVO'
                               CHECK (status IN ('ATIVO','INATIVO'))
);

-- ────────────────────────────────────────────────────────────
-- 3.1 TITULAÇÃO DO PROFESSOR (NOVO)
-- ────────────────────────────────────────────────────────────
CREATE TABLE professor_titulacao (
                                     id             BIGSERIAL     PRIMARY KEY,
                                     professor_id   BIGINT        NOT NULL REFERENCES professor(id) ON DELETE RESTRICT,
                                     categoria      VARCHAR(50)   NOT NULL CHECK (categoria IN ('GRADUACAO', 'ESPECIALIZACAO', 'MBA', 'MESTRADO', 'DOUTORADO', 'POS_DOUTORADO')),
                                     instituicao    VARCHAR(200)  NOT NULL,
                                     nome_curso     VARCHAR(200)  NOT NULL,
                                     ano_conclusao  INT           NOT NULL,
                                     UNIQUE (professor_id, categoria, instituicao, nome_curso)
);

-- ────────────────────────────────────────────────────────────
-- 4. CURSO
-- ────────────────────────────────────────────────────────────
CREATE TABLE curso (
                       id             BIGSERIAL     PRIMARY KEY,
                       sigla          VARCHAR(20)   NOT NULL,
                       descricao      VARCHAR(300)  NOT NULL,
                       turno          VARCHAR(20)   NOT NULL
                           CHECK (turno IN ('MATUTINO','VESPERTINO','NOTURNO','INTEGRAL')),
                       coordenador    VARCHAR(200),
                       escola_id      BIGINT        NOT NULL
                           REFERENCES escola(id) ON DELETE RESTRICT,
                       data_cadastro  DATE          NOT NULL DEFAULT CURRENT_DATE,
                       status         VARCHAR(10)   NOT NULL DEFAULT 'ATIVO'
                           CHECK (status IN ('ATIVO','INATIVO')),
                       UNIQUE (sigla, escola_id)
);

-- ────────────────────────────────────────────────────────────
-- 5. DISCIPLINA
-- ────────────────────────────────────────────────────────────
CREATE TABLE disciplina (
                            id             BIGSERIAL     PRIMARY KEY,
                            sigla          VARCHAR(20)   NOT NULL,
                            descricao      VARCHAR(300)  NOT NULL,
                            carga_horaria  INT           NOT NULL CHECK (carga_horaria > 0),
                            escola_id      BIGINT        NOT NULL
                                REFERENCES escola(id) ON DELETE RESTRICT,
                            data_cadastro  DATE          NOT NULL DEFAULT CURRENT_DATE,
                            status         VARCHAR(10)   NOT NULL DEFAULT 'ATIVO'
                                CHECK (status IN ('ATIVO','INATIVO')),
                            UNIQUE (sigla, escola_id)
);

-- ────────────────────────────────────────────────────────────
-- 6. MATRIZ
-- ────────────────────────────────────────────────────────────
CREATE TABLE matriz (
                        id             BIGSERIAL     PRIMARY KEY,
                        nome           VARCHAR(200)  NOT NULL,
                        descricao      TEXT,
                        curso_id       BIGINT        NOT NULL
                            REFERENCES curso(id) ON DELETE RESTRICT,
                        data_cadastro  DATE          NOT NULL DEFAULT CURRENT_DATE,
                        status         VARCHAR(10)   NOT NULL DEFAULT 'ATIVO'
                            CHECK (status IN ('ATIVO','INATIVO'))
);

-- ────────────────────────────────────────────────────────────
-- 7. MATRIZ_DISCIPLINA
-- ────────────────────────────────────────────────────────────
CREATE TABLE matriz_disciplina (
                                   id                BIGSERIAL PRIMARY KEY,
                                   matriz_id         BIGINT    NOT NULL REFERENCES matriz(id) ON DELETE RESTRICT,
                                   disciplina_id     BIGINT    NOT NULL REFERENCES disciplina(id) ON DELETE RESTRICT,
                                   pre_requisito_id  BIGINT             REFERENCES disciplina(id) ON DELETE RESTRICT,
                                   UNIQUE (matriz_id, disciplina_id)
);

-- ────────────────────────────────────────────────────────────
-- 8. ALUNO_MONITOR
-- ────────────────────────────────────────────────────────────
CREATE TABLE aluno_monitor (
                               id             BIGSERIAL     PRIMARY KEY,
                               matricula      VARCHAR(50)   NOT NULL,
                               nome           VARCHAR(200)  NOT NULL,
                               disciplina_id  BIGINT        NOT NULL REFERENCES disciplina(id) ON DELETE RESTRICT,
                               professor_id   BIGINT        NOT NULL REFERENCES professor(id) ON DELETE RESTRICT,
                               semestre       VARCHAR(10)   NOT NULL,
                               tipo_monitoria VARCHAR(50)   NOT NULL
                                   CHECK (tipo_monitoria IN ('PRESENCIAL','REMOTO')),
                               local          VARCHAR(200),
                               data_inicio    DATE          NOT NULL,
                               data_fim       DATE,
                               data_cadastro  DATE          NOT NULL DEFAULT CURRENT_DATE,
                               status         VARCHAR(10)   NOT NULL DEFAULT 'ATIVO' CHECK (status IN ('ATIVO','INATIVO','ENCERRADO')),
                               UNIQUE (matricula, semestre)
);

-- ────────────────────────────────────────────────────────────
-- 8.1 ALUNO_ATENDIDO (NOVO)
-- ────────────────────────────────────────────────────────────
CREATE TABLE aluno_atendido (
                                id             BIGSERIAL     PRIMARY KEY,
                                relatorio_id   BIGINT        NOT NULL,
                                matricula      VARCHAR(50)   NOT NULL,
                                nome           VARCHAR(200)  NOT NULL,
                                email          VARCHAR(200),
                                disciplina_id  BIGINT        NOT NULL REFERENCES disciplina(id) ON DELETE RESTRICT,
                                aluno_monitor_id BIGINT      NOT NULL REFERENCES aluno_monitor(id) ON DELETE RESTRICT,
                                semestre       VARCHAR(10)   NOT NULL,
                                status         VARCHAR(10)   NOT NULL DEFAULT 'ATIVO' CHECK (status IN ('ATIVO','INATIVO')),
                                data_cadastro  DATE          NOT NULL DEFAULT CURRENT_DATE,
                                UNIQUE (aluno_monitor_id, matricula, semestre)
);

-- ────────────────────────────────────────────────────────────
-- 9. MONITORIA_RELATORIO
-- ────────────────────────────────────────────────────────────
CREATE TABLE monitoria_relatorio (
                                     id           BIGSERIAL PRIMARY KEY,
                                     aluno_id     BIGINT    NOT NULL REFERENCES aluno_monitor(id) ON DELETE RESTRICT,
                                     semestre     VARCHAR(10) NOT NULL,
                                     qtd_alunos   INT      NOT NULL DEFAULT 0 CHECK (qtd_alunos >= 0),
                                     ocorrencias  TEXT,
                                     parecer      TEXT,
                                     data_cadastro DATE    NOT NULL DEFAULT CURRENT_DATE,
                                     UNIQUE (aluno_id, semestre)
);

ALTER TABLE aluno_atendido
    ADD CONSTRAINT fk_aluno_atendido_relatorio
        FOREIGN KEY (relatorio_id) REFERENCES monitoria_relatorio(id) ON DELETE RESTRICT;

-- ────────────────────────────────────────────────────────────
-- 10. ROLE
-- ────────────────────────────────────────────────────────────
CREATE TABLE role (
                      id    BIGSERIAL    PRIMARY KEY,
                      nome  VARCHAR(50)  NOT NULL UNIQUE
);

-- ────────────────────────────────────────────────────────────
-- 11. USUARIO
-- ────────────────────────────────────────────────────────────
CREATE TABLE usuario (
                         id            BIGSERIAL     PRIMARY KEY,
                         username      VARCHAR(100)  NOT NULL UNIQUE,
                         password      VARCHAR(255)  NOT NULL,
                         email         VARCHAR(200)  NOT NULL UNIQUE,
                         status        VARCHAR(10)   NOT NULL DEFAULT 'ATIVO'
                             CHECK (status IN ('ATIVO','INATIVO')),
                         professor_id  BIGINT                     REFERENCES professor(id) ON DELETE RESTRICT,
                         role_id       BIGINT        NOT NULL REFERENCES role(id) ON DELETE RESTRICT,
                         data_cadastro DATE          NOT NULL DEFAULT CURRENT_DATE
);

-- ────────────────────────────────────────────────────────────
-- ÍNDICES  
-- ────────────────────────────────────────────────────────────
CREATE INDEX idx_escola_ies          ON escola(ies_id);
CREATE INDEX idx_professor_escola    ON professor(escola_id);
CREATE INDEX idx_curso_escola        ON curso(escola_id);
CREATE INDEX idx_disciplina_escola   ON disciplina(escola_id);
CREATE INDEX idx_matriz_curso        ON matriz(curso_id);
CREATE INDEX idx_md_matriz           ON matriz_disciplina(matriz_id);
CREATE INDEX idx_md_disciplina       ON matriz_disciplina(disciplina_id);
CREATE INDEX idx_monitor_disciplina  ON aluno_monitor(disciplina_id);
CREATE INDEX idx_monitor_professor   ON aluno_monitor(professor_id);
CREATE INDEX idx_relatorio_aluno     ON monitoria_relatorio(aluno_id);
CREATE INDEX idx_usuario_professor       ON usuario(professor_id);
CREATE INDEX idx_atendido_monitor        ON aluno_atendido(aluno_monitor_id);
CREATE INDEX idx_atendido_disciplina     ON aluno_atendido(disciplina_id);
CREATE INDEX idx_atendido_relatorio      ON aluno_atendido(relatorio_id);