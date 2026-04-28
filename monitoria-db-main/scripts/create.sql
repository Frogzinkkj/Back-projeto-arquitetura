-- ────────────────────────────────────────────────────────────
-- 0. EXTENSÕES
-- ────────────────────────────────────────────────────────────
CREATE EXTENSION IF NOT EXISTS "pgcrypto";   
CREATE EXTENSION IF NOT EXISTS "unaccent";   

-- ────────────────────────────────────────────────────────────
-- 1. IES  
-- ────────────────────────────────────────────────────────────
CREATE TABLE ies (
    id        SERIAL        PRIMARY KEY,
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
    id             SERIAL        PRIMARY KEY,
    nome           VARCHAR(200)  NOT NULL,
    status         VARCHAR(10)   NOT NULL DEFAULT 'ATIVO'
                                 CHECK (status IN ('ATIVO','INATIVO')),
    coordenador    VARCHAR(200),
    ies_id         INT           NOT NULL
                                 REFERENCES ies(id)
);

-- ────────────────────────────────────────────────────────────
-- 3. PROFESSOR
-- ────────────────────────────────────────────────────────────
CREATE TABLE professor (
    id             SERIAL        PRIMARY KEY,
    matricula      VARCHAR(50)   NOT NULL UNIQUE,
    nome           VARCHAR(200)  NOT NULL,
    email          VARCHAR(200)  NOT NULL UNIQUE,
    telefone       VARCHAR(20),
    escola_id      INT           NOT NULL
                                 REFERENCES escola(id),
    data_cadastro  DATE          NOT NULL DEFAULT CURRENT_DATE,
    status         VARCHAR(10)   NOT NULL DEFAULT 'ATIVO'
                                 CHECK (status IN ('ATIVO','INATIVO'))
);

-- ────────────────────────────────────────────────────────────
-- 3.1 TITULAÇÃO DO PROFESSOR
-- ────────────────────────────────────────────────────────────
CREATE TABLE professor_titulacao (
    id             SERIAL        PRIMARY KEY,
    professor_id   INT           NOT NULL REFERENCES professor(id),
    categoria      VARCHAR(50)   NOT NULL CHECK (categoria IN ('GRADUACAO', 'ESPECIALIZACAO', 'MBA', 'MESTRADO', 'DOUTORADO', 'POS_DOUTORADO')),
    instituicao    VARCHAR(200)  NOT NULL,
    nome_curso     VARCHAR(200)  NOT NULL,
    ano_conclusao  INT           NOT NULL
);

-- ────────────────────────────────────────────────────────────
-- 4. CURSO
-- ────────────────────────────────────────────────────────────
CREATE TABLE curso (
    id             SERIAL        PRIMARY KEY,
    sigla          VARCHAR(20)   NOT NULL,
    descricao      VARCHAR(300)  NOT NULL,
    turno          VARCHAR(20)   NOT NULL
                                 CHECK (turno IN ('MATUTINO','VESPERTINO','NOTURNO','INTEGRAL')),
    coordenador    VARCHAR(200),
    escola_id      INT           NOT NULL
                                 REFERENCES escola(id),
    data_cadastro  DATE          NOT NULL DEFAULT CURRENT_DATE,
    status         VARCHAR(10)   NOT NULL DEFAULT 'ATIVO'
                                 CHECK (status IN ('ATIVO','INATIVO'))
);

-- ────────────────────────────────────────────────────────────
-- 5. DISCIPLINA
-- ────────────────────────────────────────────────────────────
CREATE TABLE disciplina (
    id             SERIAL        PRIMARY KEY,
    sigla          VARCHAR(20)   NOT NULL,
    descricao      VARCHAR(300)  NOT NULL,
    carga_horaria  INT           NOT NULL CHECK (carga_horaria > 0),
    escola_id      INT           NOT NULL
                                 REFERENCES escola(id),
    data_cadastro  DATE          NOT NULL DEFAULT CURRENT_DATE,
    status         VARCHAR(10)   NOT NULL DEFAULT 'ATIVO'
                                 CHECK (status IN ('ATIVO','INATIVO'))
);

-- ────────────────────────────────────────────────────────────
-- 6. MATRIZ 
-- ────────────────────────────────────────────────────────────
CREATE TABLE matriz (
    id             SERIAL        PRIMARY KEY,
    nome           VARCHAR(200)  NOT NULL,
    descricao      TEXT,
    curso_id       INT           NOT NULL
                                 REFERENCES curso(id),
    data_cadastro  DATE          NOT NULL DEFAULT CURRENT_DATE,
    status         VARCHAR(10)   NOT NULL DEFAULT 'ATIVO'
                                 CHECK (status IN ('ATIVO','INATIVO'))
);

-- ────────────────────────────────────────────────────────────
-- 7. MATRIZ_DISCIPLINA  
-- ────────────────────────────────────────────────────────────
CREATE TABLE matriz_disciplina (
    id                SERIAL   PRIMARY KEY,
    matriz_id         INT      NOT NULL REFERENCES matriz(id),
    disciplina_id     INT      NOT NULL REFERENCES disciplina(id),
    pre_requisito_id  INT               REFERENCES disciplina(id),
    UNIQUE (matriz_id, disciplina_id)
);

-- ────────────────────────────────────────────────────────────
-- 8. ALUNO_MONITOR
-- ────────────────────────────────────────────────────────────
CREATE TABLE aluno_monitor (
    id              SERIAL        PRIMARY KEY,
    matricula       VARCHAR(50)   NOT NULL UNIQUE,
    nome            VARCHAR(200)  NOT NULL,
    disciplina_id   INT           NOT NULL
                                  REFERENCES disciplina(id),
    professor_id    INT           NOT NULL
                                  REFERENCES professor(id),
    semestre        VARCHAR(10)   NOT NULL,
    tipo_monitoria  VARCHAR(50)   NOT NULL
                                  CHECK (tipo_monitoria IN ('BOLSISTA','VOLUNTARIO')),
    local           VARCHAR(200),
    data_inicio     DATE          NOT NULL,
    data_fim        DATE,
    data_cadastro   DATE          NOT NULL DEFAULT CURRENT_DATE,
    status          VARCHAR(10)   NOT NULL DEFAULT 'ATIVO'
                                  CHECK (status IN ('ATIVO','INATIVO','ENCERRADO'))
);
