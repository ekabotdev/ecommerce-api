package com.ekabotdev.ecommerce.product.dto;


import com.ekabotdev.ecommerce.product.enums.StockMovementReason;
import com.ekabotdev.ecommerce.product.enums.StockOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StockMovementResponse {
    private Long id;
    private Integer quantity;
    private StockOperation operation;
    private StockMovementReason reason;
    private LocalDateTime createdAt;
}
