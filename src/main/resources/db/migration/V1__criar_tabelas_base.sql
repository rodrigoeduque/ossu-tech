-- ============================================
-- MIGRATION V1: Estrutura Base do Sistema
-- ============================================

-- Tabela de usuários (base para alunos e professores)
CREATE TABLE usuarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) UNIQUE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL CHECK (tipo_usuario IN ('ALUNO', 'PROFESSOR')),
    ativo BOOLEAN DEFAULT true,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de faixas
CREATE TABLE faixas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(50) NOT NULL,
    cor_hex VARCHAR(7) NOT NULL,
    ordem INTEGER NOT NULL UNIQUE,
    tempo_minimo_minutos INTEGER NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de graus
CREATE TABLE graus (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    faixa_id UUID NOT NULL REFERENCES faixas(id),
    numero INTEGER NOT NULL,
    tempo_minimo_minutos INTEGER NOT NULL,
    descricao TEXT,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(faixa_id, numero)
);

-- Tabela de graduações (combinação faixa + grau)
CREATE TABLE graduacoes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    faixa_id UUID NOT NULL REFERENCES faixas(id),
    grau_id UUID REFERENCES graus(id),
    nome_display VARCHAR(100) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(faixa_id, grau_id)
);

-- Tabela de alunos
CREATE TABLE alunos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID UNIQUE NOT NULL REFERENCES usuarios(id),
    nome_completo VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    telefone VARCHAR(20),
    foto_url VARCHAR(500),
    graduacao_atual_id UUID REFERENCES graduacoes(id),
    tempo_acumulado_minutos INTEGER DEFAULT 0,
    data_inicio DATE NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de professores
CREATE TABLE professores (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID UNIQUE NOT NULL REFERENCES usuarios(id),
    nome_completo VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    graduacao_id UUID REFERENCES graduacoes(id),
    pode_aprovar_presencas BOOLEAN DEFAULT true,
    pode_graduar_alunos BOOLEAN DEFAULT false,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Histórico de graduações
CREATE TABLE historico_graduacoes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    aluno_id UUID NOT NULL REFERENCES alunos(id),
    graduacao_anterior_id UUID REFERENCES graduacoes(id),
    graduacao_nova_id UUID NOT NULL REFERENCES graduacoes(id),
    professor_responsavel_id UUID NOT NULL REFERENCES professores(id),
    data_graduacao TIMESTAMP NOT NULL,
    observacoes TEXT,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de QR Codes para treinos
CREATE TABLE qr_codes_treino (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    token VARCHAR(100) UNIQUE NOT NULL,
    data_treino DATE NOT NULL,
    horario_inicio TIME NOT NULL,
    horario_fim TIME NOT NULL,
    ativo BOOLEAN DEFAULT true,
    criado_por_professor_id UUID NOT NULL REFERENCES professores(id),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expira_em TIMESTAMP NOT NULL
);

-- Tipo enum para status de presença
CREATE TYPE status_presenca AS ENUM ('PENDENTE', 'APROVADA', 'REJEITADA', 'CANCELADA');

-- Tabela de presenças
CREATE TABLE presencas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    aluno_id UUID NOT NULL REFERENCES alunos(id),
    qr_code_id UUID NOT NULL REFERENCES qr_codes_treino(id),
    horario_checkin TIMESTAMP NOT NULL,
    horario_checkout TIMESTAMP,
    status status_presenca DEFAULT 'PENDENTE',
    tempo_treino_minutos INTEGER,
    observacao_aluno TEXT,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de aprovações de presença
CREATE TABLE aprovacoes_presenca (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    presenca_id UUID NOT NULL REFERENCES presencas(id),
    professor_id UUID NOT NULL REFERENCES professores(id),
    aprovado BOOLEAN NOT NULL,
    motivo_rejeicao TEXT,
    data_aprovacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(presenca_id)
);

-- Índices para performance
CREATE INDEX idx_alunos_usuario ON alunos(usuario_id);
CREATE INDEX idx_alunos_graduacao ON alunos(graduacao_atual_id);
CREATE INDEX idx_professores_usuario ON professores(usuario_id);
CREATE INDEX idx_presencas_aluno ON presencas(aluno_id);
CREATE INDEX idx_presencas_status ON presencas(status);
CREATE INDEX idx_presencas_qrcode ON presencas(qr_code_id);
CREATE INDEX idx_qrcodes_ativo ON qr_codes_treino(ativo, expira_em);
CREATE INDEX idx_historico_aluno ON historico_graduacoes(aluno_id);