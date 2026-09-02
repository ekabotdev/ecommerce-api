package com.ekabotdev.ecommerce.product.dto;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {

    @NotBlank
    @Size(min = 2, max = 150)
    private String name;

    private String description;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;


    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    private Long categoryId;
}
