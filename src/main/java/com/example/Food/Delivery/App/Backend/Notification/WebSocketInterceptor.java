package com.example.Food.Delivery.App.Backend.Notification;

import com.example.Food.Delivery.App.Backend.Authentication.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {
    @Autowired
    private JwtService jwtService;
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String token=getTokenFromRequest(request);
        if(token == null){
            return false;
        }
        else{
            if(jwtService.isTokenValid(token)){
                String username= jwtService.extractUsername(token);
                attributes.put("username",username);
                return true;
            }
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
    private String getTokenFromRequest(ServerHttpRequest request) {
        var headers = request.getHeaders();
        if (headers.containsKey("Authorization")) {
            String authHeader = headers.getFirst("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7);
            }
        }
        // fallback: query parameter
        String uri = request.getURI().toString();
        if (uri.contains("token=")) {
            return uri.substring(uri.indexOf("token=") + 6);
        }
        return null;
    }
}
