package com.ekabotdev.ecommerce.product.specification;

import com.ekabotdev.ecommerce.product.entity.Product;
import com.ekabotdev.ecommerce.product.enums.ProductStatus;
import com.ekabotdev.ecommerce.product.exception.InvalidProductFilterException;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {



    public static Specification<Product> search(String search) {


        return ((root, query, criteriaBuilder)
                ->  criteriaBuilder.or(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + search.toLowerCase() + "%"
                        ),
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("description")),
                        "%" + search.toLowerCase() + "%"

                )
           )
        );
    }

    public static Specification<Product> hasCategory(Long categoryId) {

        if (categoryId != null && categoryId <= 0)
            throw new InvalidProductFilterException("category id must be greater than 0");

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("category").get("id"),
                        categoryId
                )
        );

}
    public static Specification<Product> priceGreaterThanOrEqualTo(
            BigDecimal minPrice
    ) {
        if (minPrice != null  && minPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidProductFilterException("minPrice cannot be negative");
        }

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(
                        root.get("price"),
                        minPrice));
    }
    public static Specification<Product> priceLessThanOrEqualTo(
            BigDecimal maxPrice
    )
    {
        if  (maxPrice != null  && maxPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidProductFilterException("maxPrice cannot be negative");
        }

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(
                        root.get("price"),
                        maxPrice
                ));
       }

       public static Specification<Product> hasStatus(
               ProductStatus status
       ) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("status"),
                        status
                ));
       }
    }

