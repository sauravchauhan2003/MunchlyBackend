package com.example.Food.Delivery.App.Backend.Notification;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final HashMap<String,WebSocketSession> userSessions=new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = (String) session.getAttributes().get("username");
        if (username != null) {
            userSessions.put(username, session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userSessions.values().remove(session);
    }
    public void sendMessage(String message,String username) throws Exception {
        if(userSessions.containsKey(username)){
            userSessions.get(username).sendMessage(new TextMessage(message));
        }
        else{
            throw new Exception();
        }
    }
}
