package com.ekabotdev.ecommerce.order.service;

import com.ekabotdev.ecommerce.order.dto.CreateOrderRequest;
import com.ekabotdev.ecommerce.order.dto.OrderItemRequest;
import com.ekabotdev.ecommerce.order.dto.OrderItemResponse;
import com.ekabotdev.ecommerce.order.dto.OrderResponse;
import com.ekabotdev.ecommerce.order.entity.Order;
import com.ekabotdev.ecommerce.order.entity.OrderItem;
import com.ekabotdev.ecommerce.order.enums.OrderStatus;
import com.ekabotdev.ecommerce.order.exception.InvalidOrderException;
import com.ekabotdev.ecommerce.order.repository.OrderItemRepository;
import com.ekabotdev.ecommerce.order.repository.OrderRepository;
import com.ekabotdev.ecommerce.product.entity.Product;
import com.ekabotdev.ecommerce.product.entity.StockMovement;
import com.ekabotdev.ecommerce.product.enums.StockMovementReason;
import com.ekabotdev.ecommerce.product.enums.StockOperation;
import com.ekabotdev.ecommerce.product.exception.InsufficientStockException;
import com.ekabotdev.ecommerce.product.exception.ProductNotFoundException;
import com.ekabotdev.ecommerce.product.repository.ProductRepository;
import com.ekabotdev.ecommerce.product.repository.StockMovementRepository;
import com.ekabotdev.ecommerce.user.entity.User;
import com.ekabotdev.ecommerce.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;
    private final UserRepository userRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ProductRepository productRepository,
            StockMovementRepository stockMovementRepository,
            UserRepository userRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponse createOrder(
            CreateOrderRequest request,
            String email,
            String idempotencyKey
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        validateDuplicateProducts(request);


        Optional<Order> existingOrder =
                orderRepository.findByUserAndIdempotencyKey(
                        user,
                        idempotencyKey
                );

        if (existingOrder.isPresent()) {
            return toResponse(existingOrder.get());
        }

        Order order = new Order();

        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setTotal(BigDecimal.ZERO);
        order.setIdempotencyKey(idempotencyKey);

        Order savedOrder = orderRepository.save(order);


        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(
                    itemRequest.getProductId()
            ).orElseThrow(() ->
                    new ProductNotFoundException(
                            "Product with id "
                                    + itemRequest.getProductId()
                                    + " not found"
                    )
            );

            int requestedQuantity = itemRequest.getQuantity();

            if (product.getStockQuantity() < requestedQuantity) {

                throw new InsufficientStockException(
                        "Insufficient stock for product '"
                                + product.getName()
                                + "'. Available: "
                                + product.getStockQuantity()
                );
            }

            BigDecimal unitPrice = product.getPrice();

            BigDecimal subtotal = unitPrice.multiply(
                    BigDecimal.valueOf(requestedQuantity)
            );

            total = total.add(subtotal);

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(requestedQuantity);
            orderItem.setUnitPrice(unitPrice);

            orderItemRepository.save(orderItem);

            product.setStockQuantity(
                    product.getStockQuantity() - requestedQuantity
            );

            updateProductStatus(product);

            StockMovement movement = new StockMovement();

            movement.setProduct(product);
            movement.setQuantity(requestedQuantity);
            movement.setOperation(StockOperation.DECREASE);
            movement.setReason(StockMovementReason.SALE);

            stockMovementRepository.save(movement);
        }

        savedOrder.setTotal(total);
        savedOrder.setStatus(OrderStatus.CONFIRMED);

        return toResponse(savedOrder);
    }

    private void validateDuplicateProducts(
            CreateOrderRequest request
    ) {

        Set<Long> productIds = new HashSet<>();

        for (OrderItemRequest item : request.getItems()) {

            if (!productIds.add(item.getProductId())) {
                throw new InvalidOrderException(
                        "An order cannot contain the same product more than once"
                );
            }
        }
    }

    private void updateProductStatus(Product product) {

        if (product.getStockQuantity() == 0) {
            product.setStatus(
                    com.ekabotdev.ecommerce.product.enums.ProductStatus.OUT_OF_STOCK
            );
        } else {
            product.setStatus(
                    com.ekabotdev.ecommerce.product.enums.ProductStatus.ACTIVE
            );
        }
    }

    private OrderResponse toResponse(Order order) {

        List<OrderItemResponse> items =
                orderItemRepository
                        .findAllByOrderId(order.getId())
                        .stream()
                        .filter(item ->
                                item.getOrder().getId()
                                        .equals(order.getId())
                        )
                        .map(item -> {

                            BigDecimal subtotal =
                                    item.getUnitPrice().multiply(
                                            BigDecimal.valueOf(
                                                    item.getQuantity()
                                            )
                                    );

                            return new OrderItemResponse(
                                    item.getId(),
                                    item.getProduct().getId(),
                                    item.getProductName(),
                                    item.getQuantity(),
                                    item.getUnitPrice(),
                                    subtotal
                            );
                        })
                        .toList();

        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getTotal(),
                items,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}
