CREATE TABLE orders (
    id BIGINT PRIMARY KEY  AUTO_INCREMENT,
    user_id BIGINT NOT NULL ,
    status VARCHAR(20) NOT NULL ,
    total DECIMAL(19,2) NOT NULL ,
    created_at DATETIME NOT NULL ,
    updated_at DATETIME NOT NULL,


                    CONSTRAINT fk_order_user
                    FOREIGN KEY (user_id)
                    REFERENCES users(id)
);

CREATE TABLE order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL ,
    product_id BIGINT NOT NULL ,
    product_name VARCHAR(150) NOT NULL ,
    quantity INT NOT NULL ,
    unit_price DECIMAL(19,2) NOT NULL ,
    created_at DATETIME NOT NULL ,
    updated_at DATETIME NOT NULL ,

    CONSTRAINT fk_order_item_order
                         FOREIGN KEY (order_id)
                         REFERENCES orders(id),

    CONSTRAINT fk_order_item_product
                         FOREIGN KEY (product_id)
                         REFERENCES products(id)


);