create TABLE stock_movements (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL ,
    quantity INT NOT NULL ,
    operation VARCHAR(20) NOT NULL ,
    reason VARCHAR(30) NOT NULL ,
    created_at  DATETIME NOT NULL ,
    updated_at DATETIME NOT NULL ,


    CONSTRAINT fk_stock_movement_product
                             FOREIGN KEY (product_id)
                             REFERENCES products(id)
);