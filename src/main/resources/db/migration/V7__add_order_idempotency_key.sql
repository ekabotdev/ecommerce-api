ALTER TABLE orders
    ADD COLUMN idempotency_key VARCHAR(100) NOT NULL;

ALTER TABLE orders
    ADD CONSTRAINT uk_order_user_idempotency_key
        UNIQUE (user_id, idempotency_key);