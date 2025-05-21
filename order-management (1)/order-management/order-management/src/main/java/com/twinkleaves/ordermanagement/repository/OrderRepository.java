package com.twinkleaves.ordermanagement.repository;

import com.twinkleaves.ordermanagement.entity.Order;
import com.twinkleaves.ordermanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(Customer customer);
    
    @Query("SELECT o.customer.id, COUNT(o) FROM Order o GROUP BY o.customer.id")
    List<Object[]> countTotalOrdersPerCustomer();

    @Query("SELECT o.customer.id, COUNT(o) as orderCount FROM Order o GROUP BY o.customer.id ORDER BY orderCount DESC")
    List<Object[]> findTop5CustomersByOrderCount();
}
