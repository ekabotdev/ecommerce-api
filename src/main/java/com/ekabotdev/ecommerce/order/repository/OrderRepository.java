package com.ekabotdev.ecommerce.order.repository;

import com.ekabotdev.ecommerce.order.entity.Order;
import com.ekabotdev.ecommerce.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long> {
    Page<Order> findAllByUser(User user, Pageable pageable);
    Optional<Order> findByIdAndUser(Long id, User user);
    Optional<Order> findByUserAndIdempotencyKey(
            User user,
            String idempotencyKey
    );

}
