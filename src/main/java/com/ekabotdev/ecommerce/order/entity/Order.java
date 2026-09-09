package com.ekabotdev.ecommerce.order.entity;


import com.ekabotdev.ecommerce.common.entity.BaseEntity;
import com.ekabotdev.ecommerce.order.enums.OrderStatus;
import com.ekabotdev.ecommerce.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Getter
@Setter

public class Order  extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal total;

    @Column(name = "idempotency_key", nullable = false, length = 100)
    private String idempotencyKey;
}
