package com.ekabotdev.ecommerce.product.entity;


import com.ekabotdev.ecommerce.category.entity.Category;
import com.ekabotdev.ecommerce.common.entity.BaseEntity;
import com.ekabotdev.ecommerce.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "products")
@Getter
@Setter
public class Product extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Version
    @Column(nullable = false)
    private Long version;

}
