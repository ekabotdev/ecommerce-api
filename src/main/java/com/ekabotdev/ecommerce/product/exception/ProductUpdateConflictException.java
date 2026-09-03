package com.ekabotdev.ecommerce.product.exception;

import com.ekabotdev.ecommerce.product.entity.Product;

public class ProductUpdateConflictException extends RuntimeException {
    public ProductUpdateConflictException(String message) {
        super(message);
    }
}
