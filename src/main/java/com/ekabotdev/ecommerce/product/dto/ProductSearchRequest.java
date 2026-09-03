package com.ekabotdev.ecommerce.product.dto;


import com.ekabotdev.ecommerce.product.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductSearchRequest {

    private String search;

    private Long categoryId;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private ProductStatus status;
}
