INSERT INTO ies (nome, endereco, telefone, status) VALUES
    ('Universidade Católica do Salvador', 'Av. pinto aguiar, 2461 - Pituaçu, Salvador - BA', '(71) 3272-2000', 'ATIVO');

INSERT INTO escola (nome, coordenador, ies_id, status) VALUES
    ('Escola de Educação, Cultura e Humanidades', 'A designar', 1, 'ATIVO'),
    ('Escola de Ciências Sociais e Aplicadas', 'A designar', 1, 'ATIVO'),
    ('Escola de Engenharias e Ciências Tecnológicas', 'A designar', 1, 'ATIVO'),
    ('Escola de Ciências Naturais e da Saúde', 'A designar', 1, 'ATIVO');

INSERT INTO professor (matricula, nome, email, telefone, escola_id, status) VALUES
    ('PROF-001', 'Ana Paula Silveira',  'ana.silveira@ucsal.edu.br',   '(71) 99111-1111', 3, 'ATIVO'),
    ('PROF-002', 'Carlos Eduardo Lima', 'carlos.lima@ucsal.edu.br',    '(71) 99222-2222', 3, 'ATIVO'),
    ('PROF-003', 'Mariana Souza',       'mariana.souza@ucsal.edu.br',  '(71) 99333-3333', 3, 'ATIVO'),
    ('PROF-004', 'João Ferreira',       'joao.ferreira@ucsal.edu.br',  '(71) 99444-4444', 2, 'ATIVO'),
    ('PROF-005', 'Patrícia Nunes',      'patricia.nunes@ucsal.edu.br', '(71) 99555-5555', 2, 'ATIVO'),
    ('PROF-006', 'Ricardo Barros',      'ricardo.barros@ucsal.edu.br', '(71) 99666-6666', 4, 'ATIVO');

INSERT INTO curso (sigla, descricao, turno, coordenador, escola_id, status) VALUES
    ('ADS', 'Análise e Desenvolvimento de Sistemas', 'NOTURNO',  'Ana Paula Silveira',  3, 'ATIVO'),
    ('SI',  'Sistemas de Informação',                'MATUTINO', 'Carlos Eduardo Lima', 3, 'ATIVO'),
    ('ADM', 'Administração',                         'NOTURNO',  'João Ferreira',       2, 'ATIVO'),
    ('ENF', 'Enfermagem',                            'INTEGRAL', 'Ricardo Barros',      4, 'ATIVO');

INSERT INTO disciplina (sigla, descricao, carga_horaria, escola_id, status) VALUES
    ('BD1',  'Banco de Dados I',                60, 3, 'ATIVO'),
    ('POO',  'Programação Orientada a Objetos', 80, 3, 'ATIVO'),
    ('ALG',  'Algoritmos e Lógica',             60, 3, 'ATIVO'),
    ('ENG',  'Engenharia de Software',          60, 3, 'ATIVO'),
    ('MATH', 'Matemática para Computação',      60, 3, 'ATIVO'),
    ('CONT', 'Contabilidade Geral',             60, 2, 'ATIVO'),
    ('MKTG', 'Marketing Empresarial',           60, 2, 'ATIVO'),
    ('ANAT', 'Anatomia Humana',                 80, 4, 'ATIVO');

INSERT INTO matriz (nome, descricao, curso_id, status) VALUES
                                                           ('Matriz ADS 2024',  'Grade ADS vigente a partir de 2024',  1, 'ATIVO'),
                                                           ('Matriz SI 2024',   'Grade SI vigente a partir de 2024',   2, 'ATIVO'),
                                                           ('Matriz ADM 2024',  'Grade ADM vigente a partir de 2024',  3, 'ATIVO');

INSERT INTO matriz_disciplina (matriz_id, disciplina_id, pre_requisito_id) VALUES
                                                                               (1, 3, NULL),
                                                                               (1, 2, 3),
                                                                               (1, 1, 2),
                                                                               (1, 4, 2),
                                                                               (1, 5, NULL),
                                                                               (2, 3, NULL),
                                                                               (2, 2, 3),
                                                                               (2, 1, 2),
                                                                               (2, 5, NULL),
                                                                               (3, 6, NULL),
                                                                               (3, 7, NULL);

INSERT INTO aluno_monitor
(matricula, nome, disciplina_id, professor_id, semestre, tipo_monitoria, local, data_inicio, data_fim, status)
VALUES
    ('ALU-2025-001', 'Lucas Andrade',   1, 1, '2025.1', 'PRESENCIAL',   'Lab. Informática 02', '2025-02-10', '2025-07-31', 'ATIVO'),
    ('ALU-2025-002', 'Fernanda Castro', 2, 2, '2025.1', 'REMOTO',       'Teams/Discord',       '2025-02-10', '2025-07-31', 'ATIVO'),
    ('ALU-2025-003', 'Pedro Henrique',  3, 3, '2025.1', 'PRESENCIAL',   'Lab. Informática 01', '2025-02-10', '2025-07-31', 'ATIVO'),
    ('ALU-2025-004', 'Camila Rocha',    6, 4, '2025.1', 'REMOTO',       'Teams/Discord',       '2025-02-10', '2025-07-31', 'ATIVO');

INSERT INTO monitoria_relatorio (aluno_id, semestre, qtd_alunos, ocorrencias, parecer) VALUES
                                                                                           (1, '2025.1', 18, 'Dificuldades em SQL JOIN.', 'Monitor com bom desempenho. Recomendado para renovação.'),
                                                                                           (2, '2025.1', 22, 'Dúvidas sobre herança e polimorfismo.', 'Monitor dedicado, boa didática.'),
                                                                                           (3, '2025.1', 15, 'Turma com dificuldades em lógica.', 'Realizou sessões extras. Desempenho excelente.');

INSERT INTO role (nome) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_PROFESSOR');

INSERT INTO usuario (username, password, email, status, professor_id, role_id) VALUES
                                                                                   ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@ucsal.edu.br', 'ATIVO', NULL, 1),
                                                                                   ('ana.silveira', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ana.silveira@ucsal.edu.br', 'ATIVO', 1, 2),
                                                                                   ('carlos.lima', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'carlos.lima@ucsal.edu.br', 'ATIVO', 2, 2);