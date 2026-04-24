package com.unimag.ecommercexyz.controller;

import com.unimag.ecommercexyz.dto.OrderDetailsDTO;
import com.unimag.ecommercexyz.dto.ProfileDTO;
import com.unimag.ecommercexyz.service.UserDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserDashboardController {

    private final UserDashboardService service;

    @GetMapping("/users/{userId}/dashboard")
    public ResponseEntity<ProfileDTO> getUserDashboard(@PathVariable String userId) {
        System.out.println("Iniciando getUserDashboard");
        return ResponseEntity.ok(service.findProfileAndOrderHistory(userId));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDetailsDTO> getOrderDetails(@PathVariable String orderId) {
        System.out.println("Iniciando getOrderDetails");
        return ResponseEntity.ok(service.findOrderWithItems(orderId));
    }
}
