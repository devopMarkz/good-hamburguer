CREATE TABLE tb_produto (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    valor_unitario NUMERIC(15,2) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    grupo_produto VARCHAR(50) NOT NULL
);

CREATE TABLE tb_pedido (
   id BIGSERIAL PRIMARY KEY,

   subtotal NUMERIC(15,2) NOT NULL DEFAULT 0,
   desconto NUMERIC(15,2) NOT NULL DEFAULT 0,
   valor_total NUMERIC(15,2) NOT NULL DEFAULT 0,

   data_pedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_item_pedido (
    id BIGSERIAL PRIMARY KEY,

    quantidade INTEGER NOT NULL,
    valor_unitario NUMERIC(15,2) NOT NULL,
    subtotal NUMERIC(15,2) NOT NULL,

    produto_id BIGINT NOT NULL,
    pedido_id BIGINT NOT NULL,

    CONSTRAINT fk_item_produto
        FOREIGN KEY (produto_id)
            REFERENCES tb_produto (id),

    CONSTRAINT fk_item_pedido
        FOREIGN KEY (pedido_id)
            REFERENCES tb_pedido (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_item_pedido_pedido_id
    ON tb_item_pedido(pedido_id);

CREATE INDEX idx_item_pedido_produto_id
    ON tb_item_pedido(produto_id);