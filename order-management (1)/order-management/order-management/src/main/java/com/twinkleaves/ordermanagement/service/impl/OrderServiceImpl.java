package com.twinkleaves.ordermanagement.service.impl;

import com.twinkleaves.ordermanagement.entity.*;
import com.twinkleaves.ordermanagement.exception.ResourceNotFoundException;
import com.twinkleaves.ordermanagement.repository.CustomerRepository;
import com.twinkleaves.ordermanagement.repository.OrderRepository;
import com.twinkleaves.ordermanagement.repository.ProductRepository;
import com.twinkleaves.ordermanagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    public Order createOrder(Long customerId, List<Long> productIds, List<Integer> quantities) {
        if (productIds.size() != quantities.size()) {
            throw new IllegalArgumentException("Product count and quantity count mismatch");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        Order order = Order.builder()
                .customer(customer)
                .createdAt(LocalDateTime.now())
                .build();

        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            int quantity = quantities.get(i);

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

            if (product.getStock() < quantity) {
                throw new IllegalArgumentException("Insufficient stock for product ID: " + product.getId());
            }

            product.setStock(product.getStock() - quantity); // deduct stock

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(quantity)
                    .price(product.getPrice()) // price at time of order
                    .build();

            orderItems.add(item);
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
    }

    @Override
    public List<Order> getOrdersByCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        return orderRepository.findByCustomer(customer);
    }
}
