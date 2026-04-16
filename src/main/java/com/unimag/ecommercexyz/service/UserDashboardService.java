package com.unimag.ecommercexyz.service;

import com.unimag.ecommercexyz.dto.OrderDetailsDTO;
import com.unimag.ecommercexyz.dto.ProfileDTO;

public interface UserDashboardService {
    ProfileDTO findProfileAndOrderHistory(String userId);
    OrderDetailsDTO findOrderWithItems(String orderId);
}
