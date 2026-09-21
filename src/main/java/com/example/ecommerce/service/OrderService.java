package com.example.ecommerce.service;

import com.example.ecommerce.entity.Address;
import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.Product;

import com.example.ecommerce.repository.AddressRepository;
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
    private final AddressRepository addressRepository;


    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CartRepository cartRepository,
            ProductRepository productRepository,
            AddressRepository addressRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
    }


    // =========================================================
    // CREATE ORDER
    // =========================================================

    @Transactional
    public Order createOrder(
            Long userId,
            Long addressId,
            String paymentMethod) {

        // -----------------------------------------------------
        // 1. Check address
        // -----------------------------------------------------

        Address address =
                addressRepository
                        .findById(addressId)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Address not found"
                                )
                        );


        // -----------------------------------------------------
        // 2. Check address belongs to user
        // -----------------------------------------------------

        if (!address.getUserId().equals(userId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot use this address"
            );
        }


        // -----------------------------------------------------
        // 3. Get user's cart
        // -----------------------------------------------------

        List<CartItem> cartItems =
                cartRepository.findByUserId(userId);


        // -----------------------------------------------------
        // 4. Check cart
        // -----------------------------------------------------

        if (cartItems.isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cart is empty"
            );
        }


        // -----------------------------------------------------
        // 5. Calculate total and check stock
        // -----------------------------------------------------

        double totalAmount = 0;


        for (CartItem cartItem : cartItems) {

            Product product =
                    productRepository
                            .findById(
                                    cartItem.getProductId()
                            )
                            .orElseThrow(() ->
                                    new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,
                                            "Product not found"
                                    )
                            );


            if (product.getStock()
                    < cartItem.getQuantity()) {

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


        // -----------------------------------------------------
        // 6. Create order
        // -----------------------------------------------------

        Order order =
                new Order(
                        userId,
                        addressId,
                        totalAmount,
                        paymentMethod,
                        "PLACED"
                );


        Order savedOrder =
                orderRepository.save(order);


        // -----------------------------------------------------
        // 7. Create order items + reduce stock
        // -----------------------------------------------------

        for (CartItem cartItem : cartItems) {

            Product product =
                    productRepository
                            .findById(
                                    cartItem.getProductId()
                            )
                            .orElseThrow(() ->
                                    new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,
                                            "Product not found"
                                    )
                            );


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


        // -----------------------------------------------------
        // 8. Clear cart
        // -----------------------------------------------------

        cartRepository.deleteAll(cartItems);


        // -----------------------------------------------------
        // 9. Return saved order
        // -----------------------------------------------------

        return savedOrder;
    }


    // =========================================================
    // GET ORDERS OF LOGGED-IN USER
    // =========================================================

    public List<Order> getOrders(Long userId) {

        return orderRepository.findOrdersByUserId(userId);
    }


    // =========================================================
    // GET ORDER ITEMS
    // =========================================================

    public List<OrderItem> getOrderItems(
            Long orderId,
            Long userId) {

        // Check ownership

        orderRepository
                .findByIdAndUserId(
                        orderId,
                        userId
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Order not found"
                        )
                );


        return orderItemRepository
                .findByOrderId(orderId);
    }


    // =========================================================
    // UPDATE ORDER STATUS
    // =========================================================

    public Order updateOrderStatus(
            Long orderId,
            Long userId,
            String newStatus) {

        Order order =
                orderRepository
                        .findByIdAndUserId(
                                orderId,
                                userId
                        )
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Order not found"
                                )
                        );


        String currentStatus =
                order.getStatus();


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


    // =========================================================
    // CANCEL ORDER
    // =========================================================

    @Transactional
    public Order cancelOrder(
            Long orderId,
            Long userId) {

        Order order =
                orderRepository
                        .findByIdAndUserId(
                                orderId,
                                userId
                        )
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Order not found"
                                )
                        );


        // Only PLACED orders can be cancelled

        if (!"PLACED".equals(
                order.getStatus())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Order cannot be cancelled after confirmation"
            );
        }


        // Get order items

        List<OrderItem> orderItems =
                orderItemRepository
                        .findByOrderId(orderId);


        // Restore stock

        for (OrderItem orderItem :
                orderItems) {

            Product product =
                    productRepository
                            .findById(
                                    orderItem.getProductId()
                            )
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


        // Change status

        order.setStatus("CANCELLED");

        return orderRepository.save(order);
    }
}