package com.twinkleaves.ordermanagement.controller;


import com.twinkleaves.ordermanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/total-orders")
    public Map<Long, Long> getTotalOrdersByCustomer() {
        return reportService.getTotalOrdersPerCustomer();
    }

    @GetMapping("/top-customers")
    public List<Map<String, Object>> getTop5CustomersByOrders() {
        return reportService.getTop5CustomersByOrderCount();
    }
}

