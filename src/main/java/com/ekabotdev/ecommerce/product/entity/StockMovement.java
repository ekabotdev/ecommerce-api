package com.ekabotdev.ecommerce.product.entity;

import com.ekabotdev.ecommerce.common.entity.BaseEntity;
import com.ekabotdev.ecommerce.product.enums.StockMovementReason;
import com.ekabotdev.ecommerce.product.enums.StockOperation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stock_movements")
@Getter
@Setter
public class StockMovement  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StockOperation operation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StockMovementReason reason;
}
