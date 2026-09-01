CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL ,
    description TEXT,
    price DECIMAL(19,2) NOT NULL ,
    stock_quantity INT NOT NULL ,
    status VARCHAR(20) NOT NULL ,
    category_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL ,
    updated_at DATETIME NOT NULL ,


    CONSTRAINT fk_product_category
                      FOREIGN KEY (category_id)
                      REFERENCES categories(id)
);