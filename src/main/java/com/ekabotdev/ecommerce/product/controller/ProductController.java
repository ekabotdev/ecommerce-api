package com.ekabotdev.ecommerce.product.controller;


import com.ekabotdev.ecommerce.product.dto.CreateProductRequest;
import com.ekabotdev.ecommerce.product.dto.ProductResponse;
import com.ekabotdev.ecommerce.product.dto.UpdateProductRequest;
import com.ekabotdev.ecommerce.product.enums.ProductStatus;
import com.ekabotdev.ecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct
            (@Valid @RequestBody CreateProductRequest request) {
        ProductResponse response = productService.createProduct(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) ProductStatus status,
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                productService.getProducts(
                        search,
                        categoryId,
                        minPrice,
                        maxPrice,
                        status,
                        pageable
                )
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct
            (@PathVariable Long id,
             @Valid @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(
                productService.updateProduct(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long id) {
      productService.deleteProduct(id);
      return ResponseEntity.noContent().build();
    }
}
