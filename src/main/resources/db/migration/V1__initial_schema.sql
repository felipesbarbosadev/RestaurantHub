-- =====================================================
-- RestaurantHub
-- Migration V1
-- Estrutura inicial do banco de dados
-- Projeto Acadêmico - Trilha Back-End
-- =====================================================

-- ===== Usuário =====
CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,

    nome VARCHAR(120) NOT NULL,

    email VARCHAR(150) NOT NULL UNIQUE,

    senha VARCHAR(255) NOT NULL,

    role VARCHAR(30) NOT NULL, -- CLIENTE, GERENTE, ADMIN...

    consentimento_lgpd BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===== Unidade =====
CREATE TABLE unidade (

    id BIGSERIAL PRIMARY KEY,

    nome VARCHAR(120) NOT NULL,

    endereco VARCHAR(255) NOT NULL,

    telefone VARCHAR(20),

    ativa BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);


-- ===== Produto =====
CREATE TABLE produto (

    id BIGSERIAL PRIMARY KEY,

    nome VARCHAR(120) NOT NULL,

    descricao VARCHAR(500),

    categoria VARCHAR(50) NOT NULL,

    imagem_url VARCHAR(255),

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

-- ===== Cardápio =====
CREATE TABLE cardapio (

    id BIGSERIAL PRIMARY KEY,

    unidade_id BIGINT NOT NULL,

    produto_id BIGINT NOT NULL,

    preco NUMERIC(10,2) NOT NULL
    CHECK (preco > 0),

    disponivel BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_cardapio_unidade
        FOREIGN KEY (unidade_id)
        REFERENCES unidade(id),

    CONSTRAINT fk_cardapio_produto
        FOREIGN KEY (produto_id)
        REFERENCES produto(id),

    CONSTRAINT uk_cardapio
        UNIQUE (unidade_id, produto_id)

);

-- ===== Estoque =====
CREATE TABLE estoque (

    id BIGSERIAL PRIMARY KEY,

    unidade_id BIGINT NOT NULL,

    produto_id BIGINT NOT NULL,

    quantidade INTEGER NOT NULL DEFAULT 0
    CHECK (quantidade >= 0),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_estoque_unidade
        FOREIGN KEY (unidade_id)
        REFERENCES unidade(id),

    CONSTRAINT fk_estoque_produto
        FOREIGN KEY (produto_id)
        REFERENCES produto(id),

    CONSTRAINT uk_estoque
        UNIQUE (unidade_id, produto_id)

);

-- ===== Pedido =====
CREATE TABLE pedido (

    id BIGSERIAL PRIMARY KEY,

    usuario_id BIGINT NOT NULL,

    unidade_id BIGINT NOT NULL,

    status VARCHAR(30) NOT NULL,

    valor_total NUMERIC(10,2)
        NOT NULL
        CHECK (valor_total >= 0),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pedido_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id),

    CONSTRAINT fk_pedido_unidade
        FOREIGN KEY (unidade_id)
        REFERENCES unidade(id)

);

-- ===== Item Pedido =====
CREATE TABLE item_pedido (

    id BIGSERIAL PRIMARY KEY,

    pedido_id BIGINT NOT NULL,

    cardapio_id BIGINT NOT NULL,

    quantidade INTEGER
        NOT NULL
        DEFAULT 1
        CHECK (quantidade > 0),

    preco_unitario NUMERIC(10,2)
        NOT NULL
        CHECK (preco_unitario > 0),

    subtotal NUMERIC(10,2)
        NOT NULL
        CHECK (subtotal >= 0),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_item_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedido(id),

    CONSTRAINT fk_item_cardapio
        FOREIGN KEY (cardapio_id)
        REFERENCES cardapio(id)

);

-- ===== Pagamento =====
CREATE TABLE pagamento (

    id BIGSERIAL PRIMARY KEY,

    pedido_id BIGINT NOT NULL UNIQUE,

    forma_pagamento VARCHAR(30) NOT NULL,

    status VARCHAR(30) NOT NULL,

    valor NUMERIC(10,2)
        NOT NULL
        CHECK (valor >= 0),

    data_pagamento TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pagamento_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedido(id)

);