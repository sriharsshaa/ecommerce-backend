package com.example.ecommerce.service;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CartRepository cartRepository,
            ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(Long userId) {

        // 1. Get user's cart
        List<CartItem> cartItems =
                cartRepository.findByUserId(userId);

        // 2. Check whether cart is empty
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 3. Calculate total
        double totalAmount = 0;

        for (CartItem cartItem : cartItems) {

            Product product =
                    productRepository
                            .findById(cartItem.getProductId())
                            .orElseThrow();

            totalAmount +=
                    product.getPrice()
                    * cartItem.getQuantity();
        }

        // 4. Create Order
        Order order =
                new Order(
                        userId,
                        totalAmount,
                        "PLACED"
                );

        Order savedOrder =
                orderRepository.save(order);

        // 5. Create OrderItems
        for (CartItem cartItem : cartItems) {

            Product product =
                    productRepository
                            .findById(cartItem.getProductId())
                            .orElseThrow();

            OrderItem orderItem =
                    new OrderItem(
                            savedOrder.getId(),
                            product.getId(),
                            cartItem.getQuantity(),
                            product.getPrice()
                    );

            orderItemRepository.save(orderItem);
        }

        // 6. Clear cart
        cartRepository.deleteAll(cartItems);

        // 7. Return order
        return savedOrder;
    }

    public List<Order> getOrders(Long userId) {

        return orderRepository.findByUserId(userId);
    }

public List<OrderItem> getOrderItems(
        Long orderId,
        Long userId) {

    // Check whether the order belongs to this user
    orderRepository
            .findByIdAndUserId(orderId, userId)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Order does not belong to this user"
                    )
            );

    // Only return items after ownership is verified
    return orderItemRepository.findByOrderId(orderId);
}

}