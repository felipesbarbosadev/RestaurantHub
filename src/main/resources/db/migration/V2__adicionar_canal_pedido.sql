ALTER TABLE pedido
ADD COLUMN canal_pedido VARCHAR(30);

UPDATE pedido
SET canal_pedido = 'BALCAO'
WHERE canal_pedido IS NULL;

ALTER TABLE pedido
ALTER COLUMN canal_pedido SET NOT NULL;