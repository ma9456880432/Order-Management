package com.twinkleaves.ordermanagement.service;

import com.twinkleaves.ordermanagement.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Long customerId, List<Long> productIds, List<Integer> quantities);
    Order getOrderById(Long id);
    List<Order> getOrdersByCustomer(Long customerId);
}
