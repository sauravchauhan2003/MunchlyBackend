package com.example.Food.Delivery.App.Backend.Notification;

import com.example.Food.Delivery.App.Backend.OrderManagement.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebSocketHandler webSocketHandler;
    public void sendNotification(Order order,String username) throws JsonProcessingException {
        String message=objectMapper.writeValueAsString(order);
        try{
            webSocketHandler.sendMessage(message,username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
