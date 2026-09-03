package com.ekabotdev.ecommerce.product.specification;

import com.ekabotdev.ecommerce.product.exception.InvalidSortFieldException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public  final class ProductPageableValidator {

    private ProductPageableValidator() {
    }

    public static void validate(Pageable pageable) {

        for(Sort.Order  order : pageable.getSort()) {
            if(!ProductSortField.ALLOWED_FIELDS
                    .contains(order.getProperty())) {
                throw new InvalidSortFieldException("Sorting by '" + order.getProperty()
                + "is not supported");
            }
        }
    }
}
