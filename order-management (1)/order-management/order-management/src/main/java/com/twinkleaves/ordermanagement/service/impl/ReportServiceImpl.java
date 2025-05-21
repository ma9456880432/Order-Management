package com.twinkleaves.ordermanagement.service.impl;


import com.twinkleaves.ordermanagement.repository.OrderRepository;
import com.twinkleaves.ordermanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Map<Long, Long> getTotalOrdersPerCustomer() {
        List<Object[]> results = orderRepository.countTotalOrdersPerCustomer();
        return results.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> (Long) row[1]
                ));
    }

    @Override
    public List<Map<String, Object>> getTop5CustomersByOrderCount() {
        List<Object[]> results = orderRepository.findTop5CustomersByOrderCount();
        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("customerId", row[0]);
            map.put("orderCount", row[1]);
            return map;
        }).collect(Collectors.toList());
    }
}

