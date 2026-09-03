package com.ekabotdev.ecommerce.product.specification;

import java.util.Set;

public  final class ProductSortField {

    private ProductSortField() {
    }

    public static final Set<String> ALLOWED_FIELDS = Set.of(
            "name",
            "price",
            "stockQuantity",
            "createdAt",
            "updatedAt"
    );
}
