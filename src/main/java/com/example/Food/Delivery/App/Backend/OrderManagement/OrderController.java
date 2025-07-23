package com.example.Food.Delivery.App.Backend.OrderManagement;

import com.example.Food.Delivery.App.Backend.Authentication.JwtService;
import com.example.Food.Delivery.App.Backend.Authentication.MyUserRepository;
import com.example.Food.Delivery.App.Backend.Notification.NotificationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MyUserRepository myUserRepository;

    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody Map<String, Object> payload
    ) {
        // 1. Missing Authorization header
        if (authorization == null || authorization.isEmpty()) {
            return ResponseEntity.badRequest().body("Missing Authorization header");
        }

        // 2. Token must start with "Bearer "
        if (!authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Invalid Authorization format");
        }

        String token = authorization.substring(7);

        // 3. Validate token
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        // 4. Extract and validate user
        String username = jwtService.extractUsername(token);
        if (myUserRepository.findByUsername(username).isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        // 5. Validate payload keys
        if (!(payload.containsKey("address") && payload.containsKey("items") && payload.containsKey("payment method"))) {
            return ResponseEntity.badRequest().body("Missing one or more required fields: address, items, payment method");
        }

        try {
            Address address = objectMapper.convertValue(payload.get("address"), Address.class);
            List<OrderItem> items = objectMapper.convertValue(payload.get("items"), new TypeReference<List<OrderItem>>() {});
            String paymentMethod = objectMapper.convertValue(payload.get("payment method"), String.class);

            Order order = orderService.placeOrder(username, address, paymentMethod, items);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid data format: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }
    @PatchMapping("/update-order-status")
    public ResponseEntity<?> updateOrderStatus(
            @RequestBody Map<String, Object> payload
    ) {
        // 1. Validate payload
        if (!payload.containsKey("orderId") || !payload.containsKey("status")) {
            return ResponseEntity.status(400).body("Missing orderId or status in request body");
        }

        try {
            Long orderId = Long.parseLong(payload.get("orderId").toString());
            String statusStr = payload.get("status").toString().toUpperCase();
            OrderStatus newStatus = OrderStatus.valueOf(statusStr);

            Order updatedOrder = orderService.updateStatus(orderId, newStatus);
            if (updatedOrder == null) {
                return ResponseEntity.status(404).body("Order not found");
            }

            // Optional: notify admin/user (username unknown now)
            // notificationService.sendNotification(updatedOrder, "system");

            return ResponseEntity.ok(updatedOrder);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Invalid status value");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrders(@RequestHeader(value = "Authorization", required = false) String authorization) {
        // 1. Check for Authorization header
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Missing or invalid Authorization header");
        }

        String token = authorization.substring(7);

        // 2. Validate token
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        // 3. Get username from token
        String username = jwtService.extractUsername(token);

        // 4. Check user exists
        if (myUserRepository.findByUsername(username).isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        // 5. Fetch and return orders
        List<Order> orders = orderService.getOrdersByUsername(username);
        return ResponseEntity.ok(orders);
    }


}
