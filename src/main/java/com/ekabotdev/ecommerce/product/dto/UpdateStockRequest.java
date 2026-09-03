package com.ekabotdev.ecommerce.product.dto;

import com.ekabotdev.ecommerce.product.entity.StockOperation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStockRequest {

    @NotNull
    @Min(1)
    private Integer quantity;

    private StockOperation operation;
}
