package com.ekabotdev.ecommerce.product.repository;

import com.ekabotdev.ecommerce.product.entity.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends
        JpaRepository<StockMovement, Long> {

    Page<StockMovement> findAllByProduct_Id(Long productId,
                                                                Pageable pageable);
}
