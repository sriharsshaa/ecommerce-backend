package com.example.ecommerce.service;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    public Order createOrder(
            Long userId,
            String paymentMethod) {

        // 1. Get user's cart
        List<CartItem> cartItems =
                cartRepository.findByUserId(userId);

        // 2. Check whether cart is empty
        if (cartItems.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cart is empty"
            );
        }

        // 3. Check stock and calculate total
        double totalAmount = 0;

        for (CartItem cartItem : cartItems) {

            Product product =
                    productRepository
                            .findById(cartItem.getProductId())
                            .orElseThrow(() ->
                                    new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,
                                            "Product not found"
                                    )
                            );

            // Check stock before placing order
            if (product.getStock() < cartItem.getQuantity()) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Not enough stock for product: "
                                + product.getName()
                );
            }

            totalAmount +=
                    product.getPrice()
                    * cartItem.getQuantity();
        }

        // 4. Create Order
        Order order =
                new Order(
                        userId,
                        totalAmount,
                        paymentMethod,
                        "PLACED"
                );

        Order savedOrder =
                orderRepository.save(order);

        // 5. Create OrderItems and reduce stock
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

            // Reduce stock
            product.setStock(
                    product.getStock()
                    - cartItem.getQuantity()
            );

            productRepository.save(product);
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

    // Update order status
    public Order updateOrderStatus(
            Long orderId,
            Long userId,
            String newStatus) {

        Order order =
                orderRepository
                        .findByIdAndUserId(orderId, userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order does not belong to this user"
                                )
                        );

        String currentStatus = order.getStatus();

        boolean validTransition =
                (currentStatus.equals("PLACED")
                        && newStatus.equals("CONFIRMED"))
                ||
                (currentStatus.equals("CONFIRMED")
                        && newStatus.equals("SHIPPED"))
                ||
                (currentStatus.equals("SHIPPED")
                        && newStatus.equals("DELIVERED"));

        if (!validTransition) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid order status transition: "
                            + currentStatus
                            + " -> "
                            + newStatus
            );
        }

        order.setStatus(newStatus);

        return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(
            Long orderId,
            Long userId) {

        Order order =
                orderRepository
                        .findByIdAndUserId(orderId, userId)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Order not found"
                                )
                        );

        if (!order.getStatus().equals("PLACED")) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Order cannot be cancelled after confirmation"
            );
        }

        // Get ordered items
        List<OrderItem> orderItems =
                orderItemRepository.findByOrderId(orderId);

        // Restore product stock
        for (OrderItem orderItem : orderItems) {

            Product product =
                    productRepository
                            .findById(orderItem.getProductId())
                            .orElseThrow(() ->
                                    new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,
                                            "Product not found"
                                    )
                            );

            product.setStock(
                    product.getStock()
                    + orderItem.getQuantity()
            );

            productRepository.save(product);
        }

        // Change order status
        order.setStatus("CANCELLED");

        return orderRepository.save(order);
    }
}