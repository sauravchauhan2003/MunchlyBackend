package com.example.Food.Delivery.App.Backend.OrderManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public Order placeOrder(String username, Address address, String paymentMethod, List<OrderItem> items){
        Order order=new Order();
        order.setOrderStatus(OrderStatus.PLACED);
        order.setAddress(address);
        if(paymentMethod.equals("COD")){
            order.setPaymentMethod(PaymentMethod.COD);
            order.setPaid(false);
        }
        else{
            order.setPaymentMethod(PaymentMethod.ONLINE);
            order.setPaid(true);
        }
        order.setItems(items);
        order.setUsername(username);
        return orderRepository.save(order);
    }
    public Order updateStatus(long order_id,OrderStatus orderStatus){
        Optional<Order> order=orderRepository.findById(order_id);
        if(order.isEmpty()){
            return null;
        }
        else{
            Order order1=order.get();
            order1.setOrderStatus(orderStatus);
            return orderRepository.save(order1);
        }
    }
    public List<Order> getOrdersByUsername(String username) {
        return orderRepository.findByUsername(username);
    }
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByOrderStatus(status);
    }

}