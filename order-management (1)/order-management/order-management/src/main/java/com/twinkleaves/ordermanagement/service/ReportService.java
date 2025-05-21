package com.twinkleaves.ordermanagement.service;

import java.util.List;
import java.util.Map;

public interface ReportService {
    Map<Long, Long> getTotalOrdersPerCustomer();
    List<Map<String, Object>> getTop5CustomersByOrderCount();
}
